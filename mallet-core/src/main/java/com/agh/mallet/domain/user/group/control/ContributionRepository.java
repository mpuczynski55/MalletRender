package com.agh.mallet.domain.user.group.control;

import com.agh.mallet.domain.user.group.entity.ContributionJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ContributionRepository extends JpaRepository<ContributionJPAEntity, Long> {

}
