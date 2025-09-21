package com.audin.junior.service.Impl;

import org.springframework.stereotype.Service;

import com.audin.junior.entity.Notification;
import com.audin.junior.entity.OtpValidation;
import com.audin.junior.repository.NotificationRepository;
import com.audin.junior.service.NotificationService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
@Getter
public class NotificationOtpDBService implements NotificationService{
    private final NotificationRepository notificationRepository;
    @Override
    public void sendOtp(OtpValidation validation) {
        Notification notification = new Notification();
        notification.setUser(validation.getUser());
        notification.setMessage("Votre code OTP est : " + validation.getOtp() + ". Ce code expirera le : " + validation.getExpiresAt());
        this.notificationRepository.save(notification);
    }

    public void welcomeUser(OtpValidation validation) {
        Notification notification = new Notification();
        notification.setUser(validation.getUser());
        notification.setMessage("Bienvenue chez TaskFlow ! Votre compte a été activé avec succès.");
        this.notificationRepository.save(notification);
    }

}
