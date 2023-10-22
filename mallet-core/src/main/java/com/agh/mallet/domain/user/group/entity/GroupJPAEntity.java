package com.agh.mallet.domain.user.group.entity;

import com.agh.mallet.domain.set.entity.SetJPAEntity;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GROUP")
public class GroupJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IDENTIFIER", nullable = false)
    private String identifier;

    @OneToMany
    @JoinColumn(name = "GROUP_ID")
    private Set<ContributionJPAEntity> contributions = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "GROUP_ID")
    private Set<SetJPAEntity> sets = new HashSet<>();

    @OneToOne
    @MapsId
    private UserJPAEntity admin;

    public GroupJPAEntity() {}

    public GroupJPAEntity(String name, String identifier, Set<ContributionJPAEntity> contributions, UserJPAEntity admin) {
        this.name = name;
        this.identifier = identifier;
        this.contributions = contributions;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Set<ContributionJPAEntity> getContributions() {
        return contributions;
    }

    public Set<SetJPAEntity> getSets() {
        return sets;
    }

    public UserJPAEntity getAdmin() {
        return admin;
    }
}
