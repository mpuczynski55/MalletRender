package com.agh.mallet.domain.group.control;

import com.agh.mallet.domain.group.entity.GroupJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupJPAEntity, Long> {

    long countAllByName(String name);

    boolean existsByIdentifier(String identifier);

}
