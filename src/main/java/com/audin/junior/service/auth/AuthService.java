package com.audin.junior.service.auth;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.audin.junior.dto.request.RegisterDTORequest;
import com.audin.junior.dto.request.ResetPasswordDTORequest;
import com.audin.junior.entity.OtpValidation;
import com.audin.junior.entity.Role;
import com.audin.junior.entity.User;
import com.audin.junior.enums.UserRole;
import com.audin.junior.enums.UserStatus;
import com.audin.junior.mapper.AuthMapper;
import com.audin.junior.repository.UserRepository;
import com.audin.junior.service.OtpValidationService;
import com.audin.junior.utils.AuthUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final OtpValidationService otpValidationService;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthUtils authUtils;

    public User register(RegisterDTORequest dto) {
        if (this.userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (this.userRepository.existsByPseudo(dto.getPseudo())) {
            throw new IllegalArgumentException("Pseudo already in use");
        }

        User user = authMapper.toEntity(dto);

        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        Role role = new Role();
        role.setName(UserRole.USER);
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);
        user = this.userRepository.save(user);
        this.otpValidationService.save(user);
        return user;
    }

    public void sendOtp(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        this.otpValidationService.save(user);
    }

    public void resetPassword(ResetPasswordDTORequest request) {
        User user = this.userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OtpValidation otpValidation = this.otpValidationService.showValidation(user);
        if (otpValidation.getOtp().equals(request.otp())) {
            if (otpValidation.getExpiresAt().isBefore(Instant.now())) {
                throw new IllegalArgumentException("OTP expired");
            }
            if (otpValidation.getConfirmedAt() != null) {
                throw new IllegalArgumentException("OTP already used");
            }
            if(!request.newPassword().equals(request.confirmNewPassword())){
                throw new IllegalArgumentException("Passwords do not match");
            }
            // Update user status to ACTIVE and set emailVerifiedAt
            user.setPassword(request.newPassword());
            this.userRepository.save(user);
            this.otpValidationService.confirmOtp(otpValidation);
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }

    public void verifyOtp(String email, String otp) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OtpValidation otpValidation = this.otpValidationService.showValidation(user);
        if (otpValidation.getOtp().equals(otp)) {
            if (otpValidation.getExpiresAt().isBefore(Instant.now())) {
                throw new IllegalArgumentException("OTP expired");
            }
            if (otpValidation.getConfirmedAt() != null) {
                throw new IllegalArgumentException("OTP already used");
            }
            // Update user status to ACTIVE and set emailVerifiedAt

            user.setStatus(UserStatus.ACTIVE);
            user.setEmailVerifiedAt(LocalDateTime.now());
            this.userRepository.save(user);
            this.otpValidationService.confirmOtp(otpValidation);
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }

    public User me(){
        User user = this.authUtils.getCurrentUser();
        return user;
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
