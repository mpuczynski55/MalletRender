package com.agh.mallet.domain.user.group.entity;

import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTRIBUTION")
public class ContributionJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "SET_PERMISSION_TYPE")
    private PermissionType setPermissionType = PermissionType.READ;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROUP_PERMISSION_TYPE")
    private PermissionType groupPermissionType = PermissionType.READ;

    @OneToOne
    @MapsId
    private UserJPAEntity contributor;

    public ContributionJPAEntity() {
    }

    public ContributionJPAEntity(PermissionType setPermissionType, PermissionType groupPermissionType, UserJPAEntity contributor) {
        this.setPermissionType = setPermissionType;
        this.groupPermissionType = groupPermissionType;
        this.contributor = contributor;
    }

    public Long getId() {
        return id;
    }

    public PermissionType getSetPermissionType() {
        return setPermissionType;
    }

    public PermissionType getGroupPermissionType() {
        return groupPermissionType;
    }

    public UserJPAEntity getContributor() {
        return contributor;
    }

}
