package com.eventmanagement.Security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class jwtUtil {
    // ✅ Plain String secret (NO Key class)
    private static final String SECRET_KEY =
            "eventmanagementsecretkeyeventmanagementsecretkey";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    // ✅ GENERATE TOKEN (email + userId + role)
    public String generateToken(String email, String userId, String role) {

        return Jwts.builder()
                .setSubject(email)                 // email
                .claim("userId", userId)           // MongoDB ID
                .claim("role", role)               // USER / ORGANIZER / ADMIN
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ✅ EXTRACT ALL CLAIMS
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ EXTRACT EMAIL
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ EXTRACT USER ID
    public String extractUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    // ✅ EXTRACT ROLE
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // ✅ TOKEN VALIDATION
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}