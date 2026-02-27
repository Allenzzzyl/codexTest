package com.myblog.user.security;

import com.myblog.user.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenService {

    private static final String USER_ID_CLAIM = "uid";

    private final SecretKey secretKey;
    private final long expirationSeconds;

    public JwtTokenService(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
        this.expirationSeconds = jwtProperties.getExpirationSeconds();
    }

    public String generateToken(Long userId, String username) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(expirationSeconds);
        return Jwts.builder()
                .subject(username)
                .claim(USER_ID_CLAIM, userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
