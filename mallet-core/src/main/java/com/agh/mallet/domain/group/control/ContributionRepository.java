package com.agh.mallet.domain.group.control;

import com.agh.mallet.domain.group.entity.ContributionJPAEntity;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<ContributionJPAEntity, Long> {

    void deleteAllByContributor(UserJPAEntity contributor);
}
