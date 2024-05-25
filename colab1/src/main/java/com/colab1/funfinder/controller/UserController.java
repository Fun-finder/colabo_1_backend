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
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.core.Authentication;

import com.colab1.funfinder.dto.JoinRequest;
import com.colab1.funfinder.dto.LoginRequest;
import com.colab1.funfinder.dto.LoginResponse;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.service.UserService;
import com.colab1.funfinder.config.JwtTokenProvider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            String loginId = request.getLoginId();
            String password = request.getPassword();
            String check = userService.authenticate(loginId, password); // db에서 사용자 확인
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
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
            }
            String loginId = (String) authentication.getName();
            Map<String, Object> profileData = userService.getProfileData(loginId);
            logger.info("LoginId: {}", loginId);
            return ResponseEntity.ok(profileData);
        } catch (Exception e) {
            logger.error("Error in getProfile: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
    
}
