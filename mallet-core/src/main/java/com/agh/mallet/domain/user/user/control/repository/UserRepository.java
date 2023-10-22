package com.agh.mallet.domain.user.user.control.repository;

import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJPAEntity, Long> {

    Optional<UserJPAEntity> findByEmail(String email);

    long countAllByUsername(String username);

}