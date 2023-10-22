package com.agh.mallet.domain.user.group.control;

import com.agh.api.ContributionDTO;
import com.agh.api.GroupDTO;
import com.agh.mallet.domain.user.group.entity.ContributionJPAEntity;
import com.agh.mallet.domain.user.group.entity.GroupJPAEntity;
import com.agh.mallet.domain.user.group.entity.PermissionType;
import com.agh.mallet.domain.user.user.control.repository.UserRepository;
import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.exception.MalletForbiddenException;
import com.agh.mallet.infrastructure.exception.MalletNotFoundException;
import com.agh.mallet.infrastructure.mapper.PermissionTypeMapper;
import com.agh.mallet.infrastructure.utils.ObjectIdentifierProvider;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class GroupService {

    public static final String INSUFFICIENT_PERMISSION_ERROR_MSG = "User has insufficient permission to edit group";
    public static final String GROUP_NOT_FOUND_ERROR_MSG = "Group with id: {0} was not found";
    private final UserService userService;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ObjectIdentifierProvider objectIdentifierProvider;
    private final ContributionRepository contributionRepository;

    public GroupService(UserService userService, UserRepository userRepository, GroupRepository groupRepository, ObjectIdentifierProvider objectIdentifierProvider, ContributionRepository contributionRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.objectIdentifierProvider = objectIdentifierProvider;
        this.contributionRepository = contributionRepository;
    }

    public void create(GroupDTO groupDTO, String creatorEmail) {
        UserJPAEntity creator = userService.getByEmail(creatorEmail);
        String groupName = groupDTO.name();
        String groupIdentifier = objectIdentifierProvider.fromGroupName(groupName);

        List<UserJPAEntity> contributors = getAllUsers(groupDTO);
        Set<ContributionJPAEntity> contributions = toContributionJPAEntities(groupDTO.contributions(), contributors);

        GroupJPAEntity groupEntity = new GroupJPAEntity(groupName, groupIdentifier, contributions, creator);

        groupRepository.save(groupEntity);
    }

    private List<UserJPAEntity> getAllUsers(GroupDTO groupDTO) {
        List<Long> contributorIds = groupDTO.contributions().stream()
                .map(contribution -> contribution.contributor().id())
                .toList();
        return userRepository.findAllById(contributorIds);
    }

    private Set<ContributionJPAEntity> toContributionJPAEntities(List<ContributionDTO> contributionDTOS, List<UserJPAEntity> contributors) {
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
                .filter(contributor -> contributor.getId().equals(contributionDTO.id()))
                .findFirst();
    }

    public void addContributions(GroupDTO groupDTO, String userEmail) {
        boolean hasPermissionToEditGroup = hasPermissionToEditGroup(userEmail);

        if (hasPermissionToEditGroup) {
            GroupJPAEntity groupEntity = get(groupDTO.id());

            List<UserJPAEntity> users = getAllUsers(groupDTO);
            Set<ContributionJPAEntity> contributionEntities = toContributionJPAEntities(groupDTO.contributions(), users);

            groupEntity.getContributions().addAll(contributionEntities);
            groupRepository.save(groupEntity);
        }

        throw new MalletForbiddenException(INSUFFICIENT_PERMISSION_ERROR_MSG);
    }

    private GroupJPAEntity get(long id) {
        return groupRepository.findById(id)
                .orElseThrow(supplyGroupNotFoundException(id));
    }

    private boolean hasPermissionToEditGroup(String userEmail) {
        return contributionRepository.findByContributorEmail(userEmail)
                .map(ContributionJPAEntity::getGroupPermissionType)
                .stream()
                .anyMatch(PermissionType.READ_WRITE::equals);
    }

    private static Supplier<MalletNotFoundException> supplyGroupNotFoundException(long groupId) {
        return () -> {
            String message = MessageFormat.format(GROUP_NOT_FOUND_ERROR_MSG, groupId);
            throw new MalletNotFoundException(message);
        };
    }

    public void delete(GroupDTO groupDTO, String userEmail) {
        GroupJPAEntity groupEntity = get(groupDTO.id());

        UserJPAEntity groupAdmin = groupEntity.getAdmin();


    }
}
