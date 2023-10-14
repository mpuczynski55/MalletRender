package com.agh.mallet.domain.user.entity;

import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_APP")
public class UserJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private LocalDate registrationDate;
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ENABLED")
    private Boolean enabled = false;

    @OneToMany(fetch = FetchType.LAZY)
    private final Set<TermJPAEntity> knownTerms = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<UserVocabularySetJPAEntity> vocabularySets = new HashSet<>();

    public UserJPAEntity() {}

    public UserJPAEntity(String username, String password, LocalDate registrationDate, String email, Set<UserVocabularySetJPAEntity> vocabularySets) {
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.email = email;
        this.vocabularySets = vocabularySets;
    }

    public UserJPAEntity(String username, String password, LocalDate registrationDate, String email) {
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<TermJPAEntity> getKnownTerms() {
        return knownTerms;
    }

    public Set<UserVocabularySetJPAEntity> getVocabularySets() {
        return vocabularySets;
    }

    public void setVocabularySets(Set<UserVocabularySetJPAEntity> vocabularySets) {
        this.vocabularySets = vocabularySets;
    }
}
