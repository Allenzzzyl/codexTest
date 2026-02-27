package com.myblog.user.security;

import com.myblog.user.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JwtTokenServiceTest {

    @Test
    void shouldGenerateAndParseToken() {
        JwtTokenService service = buildService(3600);

        String token = service.generateToken(1L, "alice");
        Claims claims = service.parseToken(token);

        Assertions.assertEquals("alice", claims.getSubject());
        Assertions.assertEquals(1, ((Number) claims.get("uid")).intValue());
        Assertions.assertTrue(service.validateToken(token));
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        JwtTokenService service = buildService(3600);
        Assertions.assertFalse(service.validateToken("invalid-token"));
    }

    private JwtTokenService buildService(long expirationSeconds) {
        JwtProperties properties = new JwtProperties();
        properties.setSecret("MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=");
        properties.setExpirationSeconds(expirationSeconds);
        return new JwtTokenService(properties);
    }
}
