package com.agh.mallet.domain.set.entity;

import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SET")
public class SetJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SETS_TERMS",
            joinColumns = @JoinColumn(name = "SET_ID"),
            inverseJoinColumns = @JoinColumn(name = "TERM_ID")
    )
    private Set<TermJPAEntity> terms = new HashSet<>();

    @OneToOne
    @MapsId
    private UserJPAEntity creator;

    public SetJPAEntity() {}

    public SetJPAEntity(SetJPAEntity setJPAEntity) {
        this.name = setJPAEntity.getName();
        this.description = setJPAEntity.getDescription();
        addTerms(setJPAEntity.getTerms());
    }

    public UserJPAEntity getCreator() {
        return creator;
    }

    public SetJPAEntity(String name, String description, Set<TermJPAEntity> terms) {
        this.name = name;
        this.description = description;
        this.terms = terms;
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

}
