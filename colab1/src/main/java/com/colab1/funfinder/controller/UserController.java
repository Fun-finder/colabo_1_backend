package com.colab1.funfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.colab1.funfinder.dto.LoginRequest;
import com.colab1.funfinder.service.UserService;
import com.colab1.funfinder.config.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/csrf")
    public ResponseEntity<?> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("token", csrfToken.getToken());
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CSRF token not found");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletResponse httpServletResponse) {
        try {
            String loginId = request.getLoginId();
            String password = request.getPassword();
            String check = userService.authenticate(loginId, password);
            Map<String, Object> responseBody = new HashMap<>();
           
            if (check != null) {
                String jwtToken = jwtTokenProvider.createToken(loginId);
                httpServletResponse.setHeader("Authorization", "Bearer " + jwtToken);
                responseBody.put("Message", "Login successful");
                responseBody.put("Token", jwtToken);
                return ResponseEntity.ok(responseBody);
            } else { 
                responseBody.put("Error", "Invalid loginId or password");
                responseBody.put("Status", HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
            }
        } catch (Exception e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if (token == null || !jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
            }
            String loginId = jwtTokenProvider.getLoginIdFromToken(token); // JwtTokenProvider에서 getUsername 메서드를 사용
            Map<String, Object> profileData = userService.getProfileData(loginId);
            logger.info("LoginId: {}", loginId);
            return ResponseEntity.ok(profileData);
        } catch (Exception e) {
            logger.error("Error in getProfile: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
}
