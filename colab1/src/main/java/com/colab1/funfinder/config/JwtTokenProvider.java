package com.colab1.funfinder.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret.accesstoken}")
    private String accessTokenSecret;

    @Value("${jwt.secret.refreshtoken}")
    private String refreshTokenSecret;

    private final int accessTokenExpirationTime = 3600000; // 1 hour
    private final int refreshTokenExpirationTime = 86400000; // 1 day

    public String createAccessToken(String loginId) {
        return Jwts.builder()
                .setSubject(loginId)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .signWith(getSigningKey(accessTokenSecret), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String loginId) {
        return Jwts.builder()
                .setSubject(loginId)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(getSigningKey(refreshTokenSecret), SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(accessTokenSecret)).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(refreshTokenSecret)).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginIdFromAccessToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey(accessTokenSecret)).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getLoginIdFromRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey(refreshTokenSecret)).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private Key getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
}
