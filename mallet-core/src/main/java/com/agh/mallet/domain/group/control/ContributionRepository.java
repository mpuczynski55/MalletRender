package com.agh.mallet.domain.group.control;

import com.agh.mallet.domain.group.entity.ContributionJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<ContributionJPAEntity, Long> {

}
