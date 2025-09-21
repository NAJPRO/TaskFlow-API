package com.audin.junior.service.Impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.audin.junior.entity.OtpValidation;
import com.audin.junior.entity.User;
import com.audin.junior.repository.OtpValidationRepository;
import com.audin.junior.service.NotificationService;
import com.audin.junior.service.OtpValidationService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Service
@AllArgsConstructor
@Getter
@Setter
public class OtpValidationServiceImpl implements OtpValidationService {

    private final OtpValidationRepository otpValidationRepository;
    private final List<NotificationService> notificationService;
    public void save(User user) {

        Optional<OtpValidation> exisValidation = otpValidationRepository.findTopByUserIdOrderByExpiresAtDesc(user.getId());
        exisValidation.ifPresent(v -> {
            otpValidationRepository.delete(v);
        });


        OtpValidation otpValidation = new OtpValidation();
        otpValidation.setUser(user);

        Instant now = Instant.now();
        Instant expiresAt = now.plus(10, ChronoUnit.MINUTES); // 10 minutes
        otpValidation.setCreatedAt(now);
        otpValidation.setExpiresAt(expiresAt);
        String code = generateOtp();
        otpValidation.setOtp(code);

        this.otpValidationRepository.save(otpValidation);
        for(NotificationService notificationService :  this.notificationService){
            notificationService.sendOtp(otpValidation);
        }
        //this.notificationService.sendOtp(otpValidation);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Génère un nombre à 6 chiffres
        return String.format("%06d", otp);
    }

    public OtpValidation showValidation(User user){
        Optional<OtpValidation> validation = this.otpValidationRepository.findTopByUserIdOrderByExpiresAtDesc(user.getId());
        if (validation.isPresent()) {
            return validation.get();
        } else {
            throw new RuntimeException("Aucune validation OTP trouvée pour l'utilisateur avec l'ID : " + user.getId());
        }
    }

    public void confirmOtp(OtpValidation otpValidation) {
        otpValidation.setConfirmedAt(Instant.now());
        this.otpValidationRepository.save(otpValidation);

        for(NotificationService notificationService :  this.notificationService){
            notificationService.welcomeUser(otpValidation);
        }
    }
}
