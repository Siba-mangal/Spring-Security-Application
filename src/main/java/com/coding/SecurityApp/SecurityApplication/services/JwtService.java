package com.coding.SecurityApp.SecurityApplication.services;


import com.coding.SecurityApp.SecurityApplication.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Service
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;


    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }



    public String generateAccessToken(User userDetails) {
        return Jwts.builder()
                .subject(userDetails.getId().toString())
                .claim("email",userDetails.getEmail())
                .claim("role", userDetails.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(User userDetails) {
        return Jwts.builder()
                .subject(userDetails.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60*60*24*30*6))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
