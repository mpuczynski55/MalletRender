package com.agh.mallet.domain.vocabularyset.control.repository;

import com.agh.mallet.domain.vocabulary.entity.Language;
import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import com.agh.mallet.domain.vocabularyset.entity.VocabularySetJPAEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


public class VocabularySetSpecification {

    private VocabularySetSpecification() {}

    public static Specification<VocabularySetJPAEntity> termsLanguageIs(Language language) {
        return (set, criteriaQuery, criteriaBuilder) -> {
            Join<TermJPAEntity, VocabularySetJPAEntity> terms = set.join("terms");
            return criteriaBuilder.equal(terms.get("language"), language);
        };
    }

}
