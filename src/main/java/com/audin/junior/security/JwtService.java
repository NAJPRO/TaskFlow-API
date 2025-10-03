package com.audin.junior.security;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.audin.junior.entity.Jwt;
import com.audin.junior.entity.RefreshToken;
import com.audin.junior.entity.User;
import com.audin.junior.repository.JwtRepository;
import com.audin.junior.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class JwtService {
    private final UserService userService;
    private final String SECRET = "a13c4b5bb2bfcf4ea8134baec33b2fd7008cfb5765836469a3e3c6ff1cfa52ab";
    public static final long JWT_EXPIRATION = 30 * 60 * 1000; // 15 minutes
    private static final String BEARER = "Bearer";
    private final JwtRepository jwtRepository;

    public Map<String, String> generateToken(String username) {
        User user = this.userService.findByEmail(username);
        final Map<String, String> token = new HashMap<>(this.generateJwt(user));
        final String finalToken = token.get(BEARER);

        this.disableTokens(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .expire(false)
                .createdAt(Instant.now())
                .token(UUID.randomUUID().toString())
                .expireAt(Instant.now().plusMillis(30 * 60 * 1000))
                .build();

        Jwt jwt = Jwt.builder()
                .desactivate(false)
                .expire(false)
                .expireAt(Instant.now().plusMillis(JWT_EXPIRATION))
                .user(user)
                .refreshToken(refreshToken)
                .token(finalToken)
                .build();

        
        this.jwtRepository.save(jwt);
        token.put("refresh", refreshToken.getToken()); // ✅ plus d’erreur
        return token;
    }

    public Jwt findByToken(String token) {
        return this.jwtRepository.findByTokenAndDesactivateAndExpire(token, false, false)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findByUserValidToken(
                user.getEmail(),
                false,
                false)
                .orElseThrow(() -> new RuntimeException("Token Invalide"));

        jwt.setDesactivate(true);
        jwt.setExpire(true);
        this.jwtRepository.save(jwt);

    }

    public Map<String, String> refrechToken(String refreshTokenMap) {
        Jwt jwt = this.jwtRepository.findByRefrechToken(refreshTokenMap)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (jwt.getRefreshToken().getExpireAt().isBefore(Instant.now())) {
            throw new RuntimeException("Token invalide");
        }
        this.disableTokens(jwt.getUser());
        return this.generateToken(jwt.getUser().getEmail());
    }

    public String getUserName(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public Date getExpireDate(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpire(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        final Claims claims = this.getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        // final long expirationTime = currentTime + 3600000; // 1 hour
        // final long expirationTime = currentTime + 30; // 1 hour
        final long expirationTime = currentTime + JWT_EXPIRATION; // 30 sec

        final Map<String, String> claims = Map.of(
                "pseudo", user.getPseudo(),
                "email", user.getEmail(),
                "role", user.getRole().getName().toString());

        final String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(user.getEmail())
                .claims(claims)
                .signWith(getSignInKey())
                .compact();
        return Map.of(BEARER, bearer);

    }

    private SecretKey getSignInKey() {
        final byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    private void disableTokens(User user) {
        final List<Jwt> jwts = this.jwtRepository.findByUser(user.getEmail()).peek(
                jwt -> {
                    jwt.setDesactivate(true);
                    jwt.setExpire(true);
                    jwt.getRefreshToken().setExpire(true);
                    jwt.getRefreshToken().setExpireAt(null);
                    jwt.setExpireAt(null);
                }).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwts);
    }

    // @Scheduled(fixedRate = 86400000) // every 24 hours
    @Scheduled(cron = "0 */1 * * * * ") // every 1 minute
    public void deleteAllExpireTokens() {
        log.info("Suppression des tokens expirés et désactivés ...");
        this.jwtRepository.deleteByExpireAndDesactivate(true, true);
    }

}
