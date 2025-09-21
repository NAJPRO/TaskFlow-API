package com.audin.junior.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audin.junior.entity.OtpValidation;

@Repository
public interface OtpValidationRepository extends JpaRepository<OtpValidation, Integer>{
    public Optional<OtpValidation> findTopByUserIdOrderByExpiresAtDesc(Integer userId);
}
