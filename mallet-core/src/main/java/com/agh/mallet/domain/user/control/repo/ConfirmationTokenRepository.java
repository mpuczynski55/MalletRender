package com.agh.mallet.domain.user.control.repo;

import com.agh.mallet.domain.user.entity.ConfirmationTokenJPAEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationTokenJPAEntity, Long> {

    Optional<ConfirmationTokenJPAEntity> findByToken(String token);
}
