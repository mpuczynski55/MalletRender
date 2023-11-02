package com.agh.mallet.domain.set.control.repository;

import com.agh.mallet.domain.term.entity.Language;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.domain.set.entity.SetJPAEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SetRepository extends JpaRepository<SetJPAEntity, Long>, JpaSpecificationExecutor<SetJPAEntity> {

    @Query("SELECT m FROM SetJPAEntity o JOIN o.terms m WHERE o.id = ?1 AND m.language =?2")
    Page<TermJPAEntity> findAllTermsBySetIdAndLanguage(long setId, Language language, Pageable pageable);

    @Query("SELECT m FROM SetJPAEntity o JOIN o.terms m WHERE m.language =?1")
    Page<SetJPAEntity> findAllByTermsLanguage(Language language, Pageable pageable);

}
