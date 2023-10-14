package com.agh.mallet.domain.vocabularyset.control.repository;

import com.agh.mallet.domain.vocabularyset.entity.VocabularySetJPAEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VocabularySetRepository extends JpaRepository<VocabularySetJPAEntity, Long>, JpaSpecificationExecutor<VocabularySetJPAEntity> {

    Page<VocabularySetJPAEntity> findAll(Pageable pageable);

}
