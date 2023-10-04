package com.agh.mallet.domain.vocabulary.control.repository;

import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends CrudRepository<TermJPAEntity, Long> {

}
