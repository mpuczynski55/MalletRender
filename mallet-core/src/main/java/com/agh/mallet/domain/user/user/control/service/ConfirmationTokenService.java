package com.agh.mallet.domain.user.user.control.service;

import com.agh.mallet.domain.user.user.control.exception.MalletTokenException;
import com.agh.mallet.domain.user.user.control.repository.ConfirmationTokenRepository;
import com.agh.mallet.domain.user.user.control.repository.UserRepository;
import com.agh.mallet.domain.user.user.entity.ConfirmationTokenJPAEntity;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    public static final String TOKEN_NOT_FOUND_EXCEPTION_MSG = "Provided token was not found";

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
    }

    public ConfirmationTokenJPAEntity save(UserJPAEntity user) {
        String token = UUID.randomUUID()
                .toString();
        LocalDateTime createdAt = LocalDateTime.now();

        ConfirmationTokenJPAEntity tokenEntity = new ConfirmationTokenJPAEntity(token, createdAt, createdAt.plusHours(24), user);

        return confirmationTokenRepository.save(tokenEntity);
    }

    public void confirmToken(String token) {
        ConfirmationTokenJPAEntity foundToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new MalletTokenException(TOKEN_NOT_FOUND_EXCEPTION_MSG, ExceptionType.NOT_FOUND));
        UserJPAEntity userJPAEntity = foundToken.getUser();

        userJPAEntity.setEnabled(true);
        foundToken.setConfirmedAt(LocalDateTime.now());

        confirmationTokenRepository.save(foundToken);
        userRepository.save(userJPAEntity);

    }

}
