package com.agh.mallet.domain.group.control;

import com.agh.api.ContributionDTO;
import com.agh.api.GroupContributionDeleteDTO;
import com.agh.api.GroupCreateDTO;
import com.agh.api.GroupDTO;
import com.agh.api.GroupSetCreateDTO;
import com.agh.api.GroupSetDTO;
import com.agh.api.GroupUpdateAdminDTO;
import com.agh.api.GroupUpdateDTO;
import com.agh.api.SetCreateDTO;
import com.agh.api.TermCreateDTO;
import com.agh.api.UserDTO;
import com.agh.mallet.domain.group.entity.ContributionJPAEntity;
import com.agh.mallet.domain.group.entity.GroupJPAEntity;
import com.agh.mallet.domain.group.entity.PermissionType;
import com.agh.mallet.domain.set.control.service.SetService;
import com.agh.mallet.domain.set.entity.SetJPAEntity;
import com.agh.mallet.domain.term.control.repository.TermRepository;
import com.agh.mallet.domain.term.entity.Language;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.domain.user.user.control.repository.UserRepository;
import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.exception.MalletNotFoundException;
import com.agh.mallet.infrastructure.mapper.GroupDTOMapper;
import com.agh.mallet.infrastructure.mapper.PermissionTypeMapper;
import com.agh.mallet.infrastructure.utils.ObjectIdentifierProvider;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GroupService {

    private static final String INSUFFICIENT_PERMISSION_PREFIX_MSG = "Insufficient permission to ";
    private static final String PERMISSION_EDIT_GROUP_ERROR_MSG = INSUFFICIENT_PERMISSION_PREFIX_MSG + "edit group";
    private static final String PERMISSION_REMOVE_GROUP_ERROR_MSG = INSUFFICIENT_PERMISSION_PREFIX_MSG + "remove group";
    private static final String PERMISSION_CHANGE_ADMIN_ERROR_MSG = INSUFFICIENT_PERMISSION_PREFIX_MSG + "change admin";
    private static final String PERMISSION_ADD_SET_ERROR_MSG = INSUFFICIENT_PERMISSION_PREFIX_MSG + "add set to group";
    private static final String PERMISSION_REMOVE_SET_ERROR_MSG = INSUFFICIENT_PERMISSION_PREFIX_MSG + "remove set from group";
    private static final String GROUP_NOT_FOUND_ERROR_MSG = "Group with id: {0} was not found";
    private static final String NEW_ADMIN_NOT_FOUND_IN_CONTRIBUTORS_EXCEPTION_MSG = "New admin id: {0} was not found in group contributors";

    private final UserService userService;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ObjectIdentifierProvider objectIdentifierProvider;
    private final ContributionService contributionService;
    private final SetService setService;
    private final TermRepository termRepository;

    public GroupService(UserService userService,
                        UserRepository userRepository,
                        GroupRepository groupRepository,
                        ObjectIdentifierProvider objectIdentifierProvider,
                        ContributionService contributionService,
                        TermRepository termRepository,
                        SetService setService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.objectIdentifierProvider = objectIdentifierProvider;
        this.contributionService = contributionService;
        this.termRepository = termRepository;
        this.setService = setService;
    }

    public GroupDTO get(long id) {
        GroupJPAEntity groupEntity = getById(id);

        return GroupDTOMapper.from(groupEntity);
    }

    @Lock(LockModeType.WRITE)
    public Long create(GroupCreateDTO groupCreateDTO, String creatorEmail) {
        UserJPAEntity creator = userService.getByEmail(creatorEmail);
        String groupName = groupCreateDTO.name();
        String groupIdentifier = objectIdentifierProvider.fromGroupName(groupName);
        List<ContributionDTO> contributions = groupCreateDTO.contributions();

        List<UserJPAEntity> contributors = userRepository.findAllById(extractCreateContributorIds(contributions));
        Set<ContributionJPAEntity> contributionEntities = toContributionJPAEntities(contributions, contributors);
        contributionEntities.add(getCreatorContribution(creator));

        GroupJPAEntity groupEntity = new GroupJPAEntity(groupName, groupIdentifier, contributionEntities, creator);

        GroupJPAEntity savedGroup = groupRepository.save(groupEntity);
        creator.addUserGroup(savedGroup);
        userService.save(creator);

        return savedGroup.getId();
    }

    private Set<Long> extractCreateContributorIds(List<ContributionDTO> contributions) {
        return contributions.stream()
                .map(ContributionDTO::contributor)
                .map(UserDTO::id)
                .collect(Collectors.toSet());
    }

    private List<Long> extractContributorIds(List<ContributionDTO> contributions) {
        return contributions.stream()
                .map(ContributionDTO::contributor)
                .map(UserDTO::id)
                .toList();
    }

    private ContributionJPAEntity getCreatorContribution(UserJPAEntity creator) {
        return new ContributionJPAEntity(PermissionType.READ_WRITE, PermissionType.READ_WRITE, creator);
    }

    private Set<ContributionJPAEntity> toContributionJPAEntities(Collection<ContributionDTO> contributionDTOS, List<UserJPAEntity> contributors) {
        return contributionDTOS.stream()
                .map(contributionDTO -> toContributionJPAEntity(contributors, contributionDTO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Optional<ContributionJPAEntity> toContributionJPAEntity(List<UserJPAEntity> contributors, ContributionDTO contributionDTO) {
        return getMatchingContributor(contributionDTO, contributors)
                .map(contributor -> new ContributionJPAEntity(PermissionTypeMapper.from(contributionDTO.setPermissionType()), PermissionTypeMapper.from(contributionDTO.groupPermissionType()), contributor));
    }

    private Optional<UserJPAEntity> getMatchingContributor(ContributionDTO contributionDTO, List<UserJPAEntity> contributors) {
        return contributors.stream()
                .filter(contributor -> contributor.getId().equals(contributionDTO.contributor().id()))
                .findFirst();
    }

    public void updateContributions(GroupUpdateDTO groupUpdateDTO, String userEmail) {
        GroupJPAEntity groupEntity = getById(groupUpdateDTO.id());

        UserContributionValidator.validateUserGroupEditPermission(userEmail, groupEntity, PERMISSION_EDIT_GROUP_ERROR_MSG);

        List<ContributionDTO> contributions = getContributionsWithoutAdmin(groupUpdateDTO, groupEntity);
        Set<ContributionDTO> contributionsToCreate = getContributionsToCreate(contributions);
        Set<ContributionDTO> contributionsToUpdate = getContributionsToUpdate(contributions);

        List<UserJPAEntity> existingContributors = userRepository.findAllById(extractContributorIds(contributions));
        Set<ContributionJPAEntity> contributionEntitiesToCreate = toContributionJPAEntities(contributionsToCreate, existingContributors);

        Set<ContributionJPAEntity> existingContributions = groupEntity.getContributions();
        updateContributions(contributionsToUpdate, existingContributions);

        existingContributions.addAll(contributionEntitiesToCreate);

        groupRepository.save(groupEntity);
    }

    private List<ContributionDTO> getContributionsWithoutAdmin(GroupUpdateDTO groupUpdateDTO, GroupJPAEntity groupEntity) {
        return groupUpdateDTO.contributions().stream()
                .filter(contribution -> !contribution.contributor().id().equals(groupEntity.getAdmin().getId()))
                .toList();
    }

    private Set<ContributionDTO> getContributionsToCreate(List<ContributionDTO> contributions) {
        return getContributions(contributions, contribution -> Objects.isNull(contribution.id()));
    }

    private Set<ContributionDTO> getContributionsToUpdate(List<ContributionDTO> contributions) {
        return getContributions(contributions, contribution -> !Objects.isNull(contribution.id()));
    }

    private Set<ContributionDTO> getContributions(List<ContributionDTO> contributions, Predicate<ContributionDTO> contributionFilter) {
        return contributions.stream()
                .filter(contributionFilter)
                .collect(Collectors.toSet());
    }

    private void updateContributions(Set<ContributionDTO> contributionsToUpdate, Set<ContributionJPAEntity> existingContributions) {
        Map<Long, ContributionJPAEntity> existingContributionById = existingContributions.stream()
                .collect(Collectors.toMap(ContributionJPAEntity::getId, Function.identity()));

        contributionsToUpdate.stream()
                .filter(contributionToUpdate -> existingContributionById.containsKey(contributionToUpdate.id()))
                .forEach(contributionToUpdate -> rebuildContributionJPAEntity(existingContributionById, contributionToUpdate));
    }

    private void rebuildContributionJPAEntity(Map<Long, ContributionJPAEntity> existingContributionById, ContributionDTO contributionToUpdate) {
        ContributionJPAEntity existingContribution = existingContributionById.get(contributionToUpdate.id());
        PermissionType groupPermissionType = PermissionTypeMapper.from(contributionToUpdate.groupPermissionType());
        PermissionType setPermissionType = PermissionTypeMapper.from(contributionToUpdate.setPermissionType());

        existingContribution.setGroupPermissionType(groupPermissionType);
        existingContribution.setSetPermissionType(setPermissionType);
    }

    public GroupJPAEntity getById(long id) {
        return groupRepository.findById(id)
                .orElseThrow(supplyGroupNotFoundException(id));
    }

    private Supplier<MalletNotFoundException> supplyGroupNotFoundException(long groupId) {
        return () -> {
            String message = MessageFormat.format(GROUP_NOT_FOUND_ERROR_MSG, groupId);
            throw new MalletNotFoundException(message);
        };
    }

    public void delete(long id, String userEmail) {
        GroupJPAEntity groupEntity = getById(id);
        UserJPAEntity user = userService.getByEmail(userEmail);

        UserContributionValidator.validateAdminRole(userEmail, groupEntity.getAdmin(), PERMISSION_REMOVE_GROUP_ERROR_MSG);

        user.deleteUserGroup(groupEntity);
        groupRepository.delete(groupEntity);
    }

    public void updateAdmin(GroupUpdateAdminDTO groupUpdateAdminDTO, String userEmail) {
        GroupJPAEntity groupEntity = getById(groupUpdateAdminDTO.groupId());

        UserContributionValidator.validateAdminRole(userEmail, groupEntity.getAdmin(), PERMISSION_CHANGE_ADMIN_ERROR_MSG);

        UserJPAEntity newAdminEntity = getNewAdminEntity(groupUpdateAdminDTO, groupEntity);
        groupEntity.setAdmin(newAdminEntity);

        groupRepository.save(groupEntity);
    }

    private UserJPAEntity getNewAdminEntity(GroupUpdateAdminDTO groupUpdateAdminDTO, GroupJPAEntity groupEntity) {
        return groupEntity.getContributions().stream()
                .map(ContributionJPAEntity::getContributor)
                .filter(contributor -> contributor.getId().equals(groupUpdateAdminDTO.newAdminId()))
                .findFirst()
                .orElseThrow(supplyNewAdminIdNotFoundInContributors(groupUpdateAdminDTO.newAdminId()));
    }


    private Supplier<MalletNotFoundException> supplyNewAdminIdNotFoundInContributors(long newAdminId) {
        String message = MessageFormat.format(NEW_ADMIN_NOT_FOUND_IN_CONTRIBUTORS_EXCEPTION_MSG, newAdminId);
        return () -> new MalletNotFoundException(message);
    }

    public void deleteContributions(GroupContributionDeleteDTO groupContributionDeleteDTO, String userEmail) {
        Long groupId = groupContributionDeleteDTO.groupId();
        GroupJPAEntity groupEntity = getById(groupId);

        UserContributionValidator.validateUserGroupEditPermission(userEmail, groupEntity, PERMISSION_EDIT_GROUP_ERROR_MSG);

        List<ContributionJPAEntity> contributionsToDelete = contributionService.getByIds(groupContributionDeleteDTO.contributionsToDeleteIds());
        Collection<ContributionJPAEntity> groupContributions = groupEntity.getContributions();

        groupContributions.removeAll(contributionsToDelete);

        groupRepository.save(groupEntity);
    }


    @Lock(LockModeType.WRITE)
    public void addSet(GroupSetDTO groupSetDTO, String userEmail) {
        GroupJPAEntity groupEntity = getById(groupSetDTO.groupId());
        UserContributionValidator.validateUserSetEditPermission(userEmail, groupEntity, PERMISSION_ADD_SET_ERROR_MSG);

        SetJPAEntity setToClone = setService.getById(groupSetDTO.setId());
        String identifier = objectIdentifierProvider.fromSetName(setToClone.getName());

        SetJPAEntity clonedSet = new SetJPAEntity(setToClone, identifier);
        groupEntity.addSet(clonedSet);

        groupRepository.save(groupEntity);
    }

    public void removeSet(GroupSetDTO groupSetDTO, String userEmail) {
        GroupJPAEntity groupEntity = getById(groupSetDTO.groupId());

        UserContributionValidator.validateUserSetEditPermission(userEmail, groupEntity, PERMISSION_REMOVE_SET_ERROR_MSG);

        SetJPAEntity setToRemove = setService.getById(groupSetDTO.setId());
        groupEntity.removeSet(setToRemove);

        groupRepository.save(groupEntity);
    }

    @Lock(LockModeType.WRITE)
    public void createSet(GroupSetCreateDTO groupSetCreateDTO, String userEmail) {
        GroupJPAEntity groupEntity = getById(groupSetCreateDTO.groupId());
        UserJPAEntity user = userService.getByEmail(userEmail);

        UserContributionValidator.validateUserSetEditPermission(userEmail, groupEntity, PERMISSION_ADD_SET_ERROR_MSG);

        SetCreateDTO set = groupSetCreateDTO.set();
        Set<TermJPAEntity> mergedTerms = getToCreateAndExistingTerms(set);
        String setTopic = set.topic();
        String setIdentifier = objectIdentifierProvider.fromSetName(setTopic);

        SetJPAEntity setJPAEntity = new SetJPAEntity(setTopic, setIdentifier, set.description(), mergedTerms, user);

        groupEntity.addSet(setJPAEntity);

        groupRepository.save(groupEntity);
    }

    private Set<TermJPAEntity> getToCreateAndExistingTerms(SetCreateDTO setCreateDTO) {
        List<TermJPAEntity> existingTerms = termRepository.findAllById(setCreateDTO.existingTermIds());
        List<TermJPAEntity> termsToCreate = getTermsToCreate(setCreateDTO);

        return Stream.concat(termsToCreate.stream(), existingTerms.stream())
                .collect(Collectors.toSet());
    }

    private List<TermJPAEntity> getTermsToCreate(SetCreateDTO setCreateDTO) {
        return setCreateDTO.termsToCreate().stream()
                .map(this::toTermJPAEntity)
                .toList();
    }

    private TermJPAEntity toTermJPAEntity(TermCreateDTO term) {
        return new TermJPAEntity(
                term.term(),
                Language.from(term.language().name()),
                term.definition(),
                toTranslationTermJPAEntity(term)
        );
    }

    private TermJPAEntity toTranslationTermJPAEntity(TermCreateDTO term) {
        return new TermJPAEntity(
                term.translation().term(),
                Language.from(term.translation().language().name()),
                term.translation().definition()
        );
    }

}
