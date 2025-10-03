package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.LoginDTORequest;
import com.audin.junior.dto.request.RegisterDTORequest;
import com.audin.junior.dto.request.ResetPasswordDTORequest;
import com.audin.junior.dto.request.VerifyOtpRequest;
import com.audin.junior.dto.response.UserDTOResponse;
import com.audin.junior.entity.User;
import com.audin.junior.mapper.AuthMapper;
import com.audin.junior.security.JwtService;
import com.audin.junior.service.auth.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService service;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    @PostMapping(path = "register")
    public @ResponseBody UserDTOResponse register(@RequestBody RegisterDTORequest dto, HttpServletResponse response) {
        log.info("REGISTER PAYLOAD : " + dto.toString());
        User user = this.service.register(dto);
        log.info("INSCRIPTION");

        Map<String, String> tokens = this.jwtService.generateToken(user.getEmail());
        String accessToken = tokens.get("Bearer");
        String refreshToken = tokens.get("refresh");

        ResponseCookie cookie = this.createHttpOnlyCookie("refreshToken", refreshToken, 60 * 60 * 720); // 30 days

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        UserDTOResponse dtoResponse = this.authMapper.toDto(user, accessToken);
        return dtoResponse;
    }

    @PostMapping(path = "login")
    public @ResponseBody UserDTOResponse login(@RequestBody LoginDTORequest request, HttpServletResponse response) {
        System.out.println("ENTRER");
        final Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (authenticate.isAuthenticated() == false) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        // return ResponseEntity.ok(Map.of("Valide", "OK")).getBody();
        Map<String, String> tokens = this.jwtService.generateToken(request.username());
        String accessToken = tokens.get("Bearer");
        String refreshToken = tokens.get("refresh");

        ResponseCookie cookie = this.createHttpOnlyCookie("refreshToken", refreshToken, 60 * 60 * 1000);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        User user = this.service.findByEmail(request.username());

        UserDTOResponse dtoResponse = this.authMapper.toDto(user, accessToken);

        return ResponseEntity.ok(dtoResponse).getBody();
    }

    @PostMapping(path = "logout")
    public void logout() {
        this.jwtService.logout();
    }

    @PostMapping(path = "refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken, 
            HttpServletResponse response) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("error", "Refresh token is missing"));
        }

        Map<String, String> tokens = jwtService.refrechToken(refreshToken);
        String newRefreshToken = tokens.get("refresh");

        ResponseCookie cookie = this.createHttpOnlyCookie("refreshToken", newRefreshToken, 60 * 60 * 1000);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        tokens.remove(newRefreshToken);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping(path = "reset-password")
    public String resetPassword(@RequestBody ResetPasswordDTORequest request) {
        this.service.resetPassword(request);
        return null;
    }

    @PostMapping(path = "request-reset-password")
    public void requestResetPassword(@RequestBody VerifyOtpRequest request) {
        this.service.sendOtp(request.getEmail());
        ;
        log.info("OTP VERIFICATION FOR RESET PASSWORD : " + request.getEmail());
    }

    @PostMapping(path = "verify-otp")
    public void verifyOtp(@RequestBody VerifyOtpRequest request) {
        this.service.verifyOtp(request.getEmail(), request.getOtp());
        log.info("VERIFICATION OTP");
    }

    private ResponseCookie createHttpOnlyCookie(String name, String value, long maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false) // Set to true in production with HTTPS
                .path("/")
                .maxAge(maxAge)
                .sameSite("None") // Adjust based on your requirements : Strict Lax None
                .build();
    }

}
