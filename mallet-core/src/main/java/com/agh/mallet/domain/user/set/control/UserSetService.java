package com.agh.mallet.domain.user.set.control;

import com.agh.api.SetBasicDTO;
import com.agh.api.TermCreateDTO;
import com.agh.api.SetCreateDTO;
import com.agh.mallet.domain.set.control.repository.SetRepository;
import com.agh.mallet.domain.set.control.service.SetService;
import com.agh.mallet.domain.set.entity.SetJPAEntity;
import com.agh.mallet.domain.term.control.repository.TermRepository;
import com.agh.mallet.domain.term.entity.Language;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.mapper.SetBasicsDTOMapper;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserSetService {

    private final UserService userService;
    private final SetService setService;
    private final SetRepository setRepository;
    private final NextChunkRebuilder nextChunkRebuilder;
    private final TermRepository termRepository;


    public UserSetService(UserService userService, SetService setService, SetRepository setRepository, NextChunkRebuilder nextChunkRebuilder, TermRepository termRepository) {
        this.userService = userService;
        this.setService = setService;
        this.setRepository = setRepository;
        this.nextChunkRebuilder = nextChunkRebuilder;
        this.termRepository = termRepository;
    }

    public SetBasicDTO get(int startPosition,
                           int limit,
                           String userEmail) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        List<SetJPAEntity> userSets = userEntity.getUserSets();
        String nextChunkUri = nextChunkRebuilder.rebuild(userSets, startPosition, limit);

        return SetBasicsDTOMapper.from(userSets, nextChunkUri);
    }

    public void add(String userEmail, long setId) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        SetJPAEntity setEntity = setService.getById(setId);

        add(setEntity, userEntity);
    }

    private void add(SetJPAEntity set, UserJPAEntity user) {
        SetJPAEntity clonedSet = new SetJPAEntity(set);

        user.addUserSet(clonedSet);

        userService.save(user);
    }


    public void remove(long setId, String userEmail) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        SetJPAEntity setEntity = setService.getById(setId);

        remove(setEntity, userEntity);
    }

    private void remove(SetJPAEntity set,
                        UserJPAEntity user) {
        user.removeUserSet(set);

        userService.save(user);
    }

    public void create(SetCreateDTO setCreateDTO, String userEmail) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        Set<TermJPAEntity> mergedTerms = getToCreateAndExistingTerms(setCreateDTO);
        SetJPAEntity setJPAEntity = new SetJPAEntity(setCreateDTO.topic(), setCreateDTO.description(), mergedTerms, userEntity);

        userEntity.getUserSets().add(setJPAEntity);

        userService.save(userEntity);
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
