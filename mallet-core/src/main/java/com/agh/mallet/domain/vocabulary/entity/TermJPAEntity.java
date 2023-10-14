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

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "TERM", nullable = false)
    private String term;
    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE", nullable = false)
    private Language language;

    //todo enum
    @Column(name = "GRAMMATICAL_CATEGORY")
    private String grammaticalCategory;

    @Column(name = "DEFINITION")
    private String definition;

    @Column(name = "TOPIC")
    private String topic;

    @Column(name = "TERM_DICTIONARY")
    private boolean isTermDictionary;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<TermJPAEntity> translations = new HashSet<>();

    public TermJPAEntity() {}

    public TermJPAEntity(String term, Language language, String grammaticalCategory, String topic) {
        this.term = term;
        this.language = language;
        this.grammaticalCategory = grammaticalCategory;
        this.topic = topic;
    }

    public TermJPAEntity(String term, Language language, String grammaticalCategory, String topic, Set<TermJPAEntity> translations) {
        this.term = term;
        this.language = language;
        this.grammaticalCategory = grammaticalCategory;
        this.topic = topic;
        this.translations = translations;
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

    public Long getId() {
        return id;
    }

    public String getTerm() {
        return term;
    }

    public Language getLanguage() {
        return language;
    }

    public String getGrammaticalCategory() {
        return grammaticalCategory;
    }

    public String getDefinition() {
        return definition;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isTermDictionary() {
        return isTermDictionary;
    }

    public Set<TermJPAEntity> getTranslations() {
        return translations;
    }
}
