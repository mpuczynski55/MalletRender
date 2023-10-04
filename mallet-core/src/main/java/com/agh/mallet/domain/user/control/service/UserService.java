package com.agh.mallet.domain.user.control.service;

import com.agh.mallet.api.UserInformationDTO;
import com.agh.mallet.api.UserLogInDTO;
import com.agh.mallet.api.UserRegistrationDTO;
import com.agh.mallet.domain.user.control.exception.MalletUserException;
import com.agh.mallet.domain.user.control.repo.UserRepository;
import com.agh.mallet.domain.user.control.utils.EmailTemplateProvider;
import com.agh.mallet.domain.user.control.utils.UserValidator;
import com.agh.mallet.domain.user.entity.ConfirmationTokenJPAEntity;
import com.agh.mallet.domain.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.exception.ExceptionType;
import com.agh.mallet.infrastructure.mapper.UserInformationDTOMapper;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class UserService {

    private static final String USER_ALREADY_EXISTS_ERROR_MSG = "User with provided email: {0} already exists";
    private static final String USER_NOT_FOUND_ERROR_MSG = "User with provided email: {0} does not exist";

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final Pbkdf2PasswordEncoder pbkdf2PasswordEncoder;

    public UserService(UserRepository userRepository, UserValidator userValidator, ConfirmationTokenService confirmationTokenService, EmailService emailService, Pbkdf2PasswordEncoder pbkdf2PasswordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
        this.pbkdf2PasswordEncoder = pbkdf2PasswordEncoder;
    }

    @Transactional
    public void signUp(UserRegistrationDTO userInfo) {
        String email = userInfo.email();
        userValidator.validateEmail(email);

        Optional<UserJPAEntity> userJPAEntity = userRepository.findByEmail(email);
        userJPAEntity.ifPresent(throwUserAlreadyExistsException());

        UserJPAEntity user = mapToUserEntity(userInfo);
        userRepository.save(user);

        ConfirmationTokenJPAEntity confirmationToken = confirmationTokenService.save(user);

        //todo confirmation link
        emailService.sendMail("Mallet account confirmation", email, EmailTemplateProvider.getEmailConfirmationTemplate(""));
    }

    private UserJPAEntity mapToUserEntity(UserRegistrationDTO userInfo) {
        String encodedPassword = pbkdf2PasswordEncoder.encode(userInfo.password());
        String username = userInfo.username();
        LocalDate registrationDate = LocalDate.now();
        String email = userInfo.email();

        return new UserJPAEntity(username, encodedPassword, registrationDate, email);
    }

    private Consumer<UserJPAEntity> throwUserAlreadyExistsException() {
        return user -> {
            String errorMsg = MessageFormat.format(USER_ALREADY_EXISTS_ERROR_MSG, user.getEmail());
            throw new MalletUserException(errorMsg, ExceptionType.ALREADY_EXISTS);
        };
    }

    public UserInformationDTO logIn(UserLogInDTO userInfo) {
        String email = userInfo.email();
        userValidator.validateEmail(email);

        UserJPAEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(throwUserNotFoundException(userInfo));

        userValidator.validateUserLogIn(userEntity, userEntity.getPassword());

        return UserInformationDTOMapper.from(userEntity);
    }

    private Supplier<RuntimeException> throwUserNotFoundException(UserLogInDTO userInfo) {
        return () -> {
            String errorMsg = MessageFormat.format(USER_NOT_FOUND_ERROR_MSG, userInfo.email());
            throw new MalletUserException(errorMsg, ExceptionType.NOT_FOUND);
        };
    }

}
