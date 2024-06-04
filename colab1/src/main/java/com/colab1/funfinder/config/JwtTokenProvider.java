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

    private final Key accesskey;
    private final Key refreshKey; 
    private final int expirationTime = 3600000;// 1시간 유효기간

    public JwtTokenProvider(@Value("${jwt.secret.accessToken}") String secretKey, @Value("${jwt.secret.refrestoken") String refreshSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The secret key must be at least 256 bits (32 bytes) long");
        }
        this.accesskey = Keys.hmacShaKeyFor(keyBytes);
        this.refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    public String createAccessToken(String loginId) {
    	Date now = new Date();
    	Date validity = new Date(now.getTime() + expirationTime); 
    	
    	return Jwts.builder()
    			.setSubject("accessToken")
    			.claim("loginId", loginId)
    			.setIssuedAt(now)
    			.setExpiration(validity)
    			.signWith(accesskey, SignatureAlgorithm.HS256)
    			.compact();
    }
    
    public String createRefreshToken() {
    	Date now = new Date();
    	Date validity = new Date(now.getTime() + expirationTime); 
    	
    	return Jwts.builder()
    			.setSubject("refreshToken")
    			.setIssuedAt(now)
    			.setExpiration(validity)
    			.signWith(refreshKey, SignatureAlgorithm.HS256)
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
            Jwts.parserBuilder().setSigningKey(accesskey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean validateRefreshToken(String token) {
    	try {
    		Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token);
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }

    public String getLoginIdFromAccessToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(accesskey).build().parseClaimsJws(token).getBody();
        return claims.get("loginId").toString();
    }
}
