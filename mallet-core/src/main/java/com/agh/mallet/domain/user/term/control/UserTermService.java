package com.agh.mallet.domain.user.term.control;

import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.control.utils.UserValidator;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.domain.term.control.repository.TermRepository;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserTermService {

    private final UserService userService;
    private final TermRepository termRepository;
    private final UserValidator userValidator;

    public UserTermService(UserService userService, TermRepository termRepository, UserValidator userValidator) {
        this.userService = userService;
        this.termRepository = termRepository;
        this.userValidator = userValidator;
    }

    public void updateKnown(Set<Long> userKnownTermIds, String userEmail){
        UserJPAEntity userEntity = userService.getByEmail(userEmail);
        userValidator.validateActiveness(userEntity);

        List<TermJPAEntity> termsToUpdate = termRepository.findAllById(userKnownTermIds);

        userEntity.getKnownTerms().addAll(termsToUpdate);

        userService.save(userEntity);
    }

}
