package com.agh.mallet.domain.vocabularyset.entity;

import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "VOCABULARY_SET")
public class VocabularySetJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<TermJPAEntity> terms;

    public VocabularySetJPAEntity() {
    }

    public VocabularySetJPAEntity(String name, String description, Set<TermJPAEntity> terms) {
        this.name = name;
        this.description = description;
        this.terms = terms;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<TermJPAEntity> getTerms() {
        return terms;
    }
}
