package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.LoginDTORequest;
import com.audin.junior.dto.request.RegisterDTORequest;
import com.audin.junior.dto.request.ResetPasswordDTORequest;
import com.audin.junior.dto.request.VerifyOtpRequest;
import com.audin.junior.entity.User;
import com.audin.junior.security.JwtService;
import com.audin.junior.service.auth.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService service;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping(path = "/register")
    public void register(@RequestBody RegisterDTORequest dto) {
        this.service.register(dto);
        log.info("INSCRIPTION");
    }

    @PostMapping(path = "login")
    public Map<String, String> login(@RequestBody LoginDTORequest request) {
        final Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if(authenticate.isAuthenticated() == false){
            throw new IllegalArgumentException("Invalid credentials");
        }

        return this.jwtService.generateToken(request.username());
    }

    @PostMapping(path = "logout")
    public void logout() {
        this.jwtService.logout();
    }

    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refrechToken(@RequestBody Map<String, String> request) {
        return this.jwtService.refrechToken(request);
    }

    @PostMapping(path = "reset-password")
    public String resetPassword(@RequestBody ResetPasswordDTORequest request) {
        this.service.resetPassword(request);
        return null;
    }


    @PostMapping(path = "request-reset-password")
    public void requestResetPassword(@RequestBody VerifyOtpRequest request) {
        this.service.sendOtp(request.getEmail());;
        log.info("OTP VERIFICATION FOR RESET PASSWORD : " + request.getEmail());
    }
    

    @PostMapping(path = "verify-otp")
    public void verifyOtp(@RequestBody VerifyOtpRequest request) {
        this.service.verifyOtp(request.getEmail(), request.getOtp());
        log.info("VERIFICATION OTP");
    }

}
