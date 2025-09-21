package com.audin.junior.entity;

import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtpValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Instant createdAt;
    private Instant expiresAt;
    private Instant confirmedAt;

    private String otp;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private User user;
}
