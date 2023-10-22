package com.agh.mallet.domain.user.user.control.utils;

import com.agh.mallet.domain.user.user.control.exception.MalletUserException;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.exception.ExceptionType;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final String INVALID_EMAIL_ERROR_MSG = "Provided email: {0} is not valid";
    private static final String USER_NOT_ENABLED_ERROR_MSG = "User with email: {0} is not enabled";
    private static final String INCORRECT_PASSWORD_ERROR_MSG = "Provided password is incorrect";

    private final Pbkdf2PasswordEncoder pbkdf2PasswordEncoder;

    public UserValidator(Pbkdf2PasswordEncoder pbkdf2PasswordEncoder) {
        this.pbkdf2PasswordEncoder = pbkdf2PasswordEncoder;
    }

    public void validateUserLogIn(UserJPAEntity user,
                                  String providedPassword) {
        validateActiveness(user);
        validatePassword(user, providedPassword);
    }

    private void validatePassword(UserJPAEntity user, String providedPassword) {
        if (!pbkdf2PasswordEncoder.matches(providedPassword, user.getPassword())) {
            throw new MalletUserException(INCORRECT_PASSWORD_ERROR_MSG, ExceptionType.INVALID_ARGUMENT);
        }
    }

    public void validateActiveness(UserJPAEntity user) {
        if (Boolean.FALSE.equals(user.getEnabled())) {
            String exceptionMessage = MessageFormat.format(USER_NOT_ENABLED_ERROR_MSG, user.getEmail());
            throw new MalletUserException(exceptionMessage, ExceptionType.LOCKED);
        }
    }

    public void validateEmail(String email) {
        if (!isEmailValid(email)) {
            String exceptionMessage = MessageFormat.format(INVALID_EMAIL_ERROR_MSG, email);
            throw new MalletUserException(exceptionMessage, ExceptionType.INVALID_ARGUMENT);
        }
    }

    private boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
