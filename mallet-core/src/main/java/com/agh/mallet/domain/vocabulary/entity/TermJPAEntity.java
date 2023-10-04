package com.agh.mallet.domain.vocabulary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TERM")
public class TermJPAEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    Set<TermJPAEntity> termTranslations = new HashSet<>();
    @ManyToMany(mappedBy = "termTranslations", fetch = FetchType.EAGER)
    Set<TermJPAEntity> translationsOfTerm = new HashSet<>();
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "TERM", nullable = false)
    private String term;
    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE", nullable = false)
    private Language language;
    @Column(name = "GRAMMATICAL_CAT")
    private String grammaticalCategory;
    @Column(name = "TOPIC")
    private String topic;

    public TermJPAEntity() {
    }

    public TermJPAEntity(String term, Language language, String grammaticalCategory, String topic, Set<TermJPAEntity> termTranslations, Set<TermJPAEntity> translationsOfTerm) {
        this.term = term;
        this.language = language;
        this.grammaticalCategory = grammaticalCategory;
        this.topic = topic;
        this.termTranslations = termTranslations;
        this.translationsOfTerm = translationsOfTerm;
    }

    public TermJPAEntity(String term, Language language, String grammaticalCategory, String topic) {
        this.term = term;
        this.language = language;
        this.grammaticalCategory = grammaticalCategory;
        this.topic = topic;
    }

    public TermJPAEntity(String term, Language language, String grammaticalCategory, String topic, Set<TermJPAEntity> termTranslations) {
        this.term = term;
        this.language = language;
        this.grammaticalCategory = grammaticalCategory;
        this.topic = topic;
        this.termTranslations = termTranslations;
    }

    public TermJPAEntity(String term, Language language, String grammaticalCategory, Set<TermJPAEntity> translationsOfTerm, String topic) {
        this.term = term;
        this.language = language;
        this.grammaticalCategory = grammaticalCategory;
        this.topic = topic;
        this.translationsOfTerm = translationsOfTerm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setGrammaticalCategory(String grammaticalCategory) {
        this.grammaticalCategory = grammaticalCategory;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTermTranslations(Set<TermJPAEntity> termTranslations) {
        this.termTranslations = termTranslations;
    }

    public void setTranslationsOfTerm(Set<TermJPAEntity> translationsOfTerm) {
        this.translationsOfTerm = translationsOfTerm;
    }
}
