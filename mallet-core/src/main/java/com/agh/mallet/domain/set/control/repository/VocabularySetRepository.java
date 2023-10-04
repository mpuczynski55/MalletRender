package com.agh.mallet.domain.set.control.repository;

import com.agh.mallet.domain.set.entity.VocabularySetJPAEntity;
import org.springframework.data.repository.CrudRepository;

public interface VocabularySetRepository extends CrudRepository<VocabularySetJPAEntity, Long> {
}
