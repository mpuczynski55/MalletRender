package com.agh.mallet.domain.set.entity;

import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SET")
public class SetJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO RENAME TOPIC??
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IDENTIFIER", nullable = false)
    private String identifier;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "SETS_TERMS",
            joinColumns = @JoinColumn(name = "SET_ID"),
            inverseJoinColumns = @JoinColumn(name = "TERM_ID")
    )
    private Set<TermJPAEntity> terms = new HashSet<>();

    @ManyToOne
    private UserJPAEntity creator;

    public SetJPAEntity() {
    }

    public SetJPAEntity(SetJPAEntity setJPAEntity) {
        this.name = setJPAEntity.getName();
        this.identifier = setJPAEntity.getIdentifier();
        this.description = setJPAEntity.getDescription();
        addTerms(setJPAEntity.getTerms());
    }

    public SetJPAEntity(String name, String identifier, String description, Set<TermJPAEntity> terms) {
        this.name = name;
        this.description = description;
        this.identifier = identifier;
        this.terms = terms;
    }

    public UserJPAEntity getCreator() {
        return creator;
    }

    public SetJPAEntity(String name, String description, String identifier, Set<TermJPAEntity> terms, UserJPAEntity creator) {
        this.name = name;
        this.identifier = identifier;
        this.description = description;
        this.terms = terms;
        this.creator = creator;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TermJPAEntity> getTerms() {
        return terms;
    }

    public void setTerms(Set<TermJPAEntity> terms) {
        this.terms = terms;
    }

    public void addTerms(Set<TermJPAEntity> terms) {
        this.terms = terms;
    }

    public void setCreator(UserJPAEntity creator) {
        this.creator = creator;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
