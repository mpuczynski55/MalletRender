package com.agh.mallet.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "CONFIRMATION_TOKEN")
public class ConfirmationTokenJPAEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private UserJPAEntity userJPAEntity;

    public ConfirmationTokenJPAEntity() {
    }

    public ConfirmationTokenJPAEntity(String token, LocalDateTime createdAt, LocalDateTime expiresAt, UserJPAEntity userJPAEntity) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.userJPAEntity = userJPAEntity;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public UserJPAEntity getUserJPAEntity() {
        return userJPAEntity;
    }
}
