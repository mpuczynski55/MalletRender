package com.agh.mallet.domain.user.control.repository;

import com.agh.mallet.domain.user.entity.UserJPAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserJPAEntity, Long> {

    Optional<UserJPAEntity> findByEmail(String email);
}