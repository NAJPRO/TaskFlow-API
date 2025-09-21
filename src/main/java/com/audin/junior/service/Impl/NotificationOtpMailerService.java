package com.audin.junior.service.Impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.audin.junior.entity.OtpValidation;
import com.audin.junior.service.NotificationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationOtpMailerService implements NotificationService{

    private final JavaMailSender mailSender;
    public void sendOtp(OtpValidation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@skydev.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Validation de votre compte");
        String text = "Bonjour " + validation.getUser().getPseudo() + ",\n\n"
                + "Votre code OTP est : " + validation.getOtp() + "\n"
                + "Ce code expirera le : " + validation.getExpiresAt() + "\n\n"
                + "Merci de ne pas partager ce code avec qui que ce soit.\n\n"
                + "Cordialement,\n"
                + "L'équipe SkyDev.";
        message.setText(text);

        mailSender.send(message);
    }

    public void welcomeUser(OtpValidation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@skydev.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Bienvenue chez TaskFlow");
        String text = "Bonjour " + validation.getUser().getPseudo() + ",\n\n"
                + "Bienvenue chez TaskFlow ! Votre compte a été activé avec succès.\n\n"
                + "Nous sommes ravis de vous compter parmi nos utilisateurs.\n\n"
                + "Cordialement,\n"
                + "L'équipe SkyDev.";
        message.setText(text);
        mailSender.send(message);
    }
}
