package com.agh.mallet.domain.user.user.control.repository;

import com.agh.mallet.domain.set.entity.SetJPAEntity;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJPAEntity, Long>, JpaSpecificationExecutor<UserJPAEntity> {

    Optional<UserJPAEntity> findByEmail(String email);

    long countAllByUsername(String username);

    List<UserJPAEntity> findAllByUsernameContainingIgnoreCase(String username);

    @Query("SELECT s FROM SetJPAEntity s WHERE s.creator.email = ?1")
    Page<SetJPAEntity> findAllSetsByUserEmail(String email, Pageable pageable);

}