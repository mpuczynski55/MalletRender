package com.agh.mallet.domain.user.group.control;

import com.agh.mallet.domain.user.group.entity.ContributionJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContributionRepository extends JpaRepository<ContributionJPAEntity, Long> {

    Optional<ContributionJPAEntity> findByContributorEmail(String email);

}
