package com.agh.mallet.domain.term.control.repository;

import com.agh.mallet.domain.term.entity.TermJPAEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TermRepository extends JpaRepository<TermJPAEntity, Long> {

    Page<TermJPAEntity> findAllByTerm(String term, Pageable pageable);
}
