package com.agh.mallet.domain.user.entity;

import com.agh.mallet.domain.vocabularyset.entity.VocabularySetJPAEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_VOCABULARY_SET")
public class UserVocabularySetJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private PermissionType permissionType = PermissionType.READ;

    @ManyToOne
    private UserJPAEntity user;

    @ManyToOne
    private VocabularySetJPAEntity vocabularySetJPAEntity;

    public UserVocabularySetJPAEntity() {
    }

    public UserVocabularySetJPAEntity(PermissionType permissionType, UserJPAEntity user, VocabularySetJPAEntity vocabularySetJPAEntity) {
        this.permissionType = permissionType;
        this.user = user;
        this.vocabularySetJPAEntity = vocabularySetJPAEntity;
    }

}
