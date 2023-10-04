package com.agh.mallet.domain.user.control.repo;

import com.agh.mallet.domain.user.entity.UserVocabularySetJPAEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserVocabularyRepository extends CrudRepository<UserVocabularySetJPAEntity, Long> {
}
