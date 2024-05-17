package com.colab1.funfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.colab1.funfinder.dto.JoinRequest;
import com.colab1.funfinder.dto.LoginRequest;
import com.colab1.funfinder.dto.LoginResponse;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        try {
            System.out.println("request.getLoginId(): " + request.getLoginId() + ", request.getPassword(): " + request.getPassword());
            User loginUser = userService.authenticate(request.getLoginId(), request.getPassword());
            
            if (loginUser != null) {
//                httpServletRequest.getSession(true).setAttribute("token", token);
                httpServletRequest.getSession(true).setAttribute("loginUser", loginUser);
//                httpServletRequest.getSession(true).setAttribute("loginId", request.getLoginId());
                httpServletRequest.getSession().setMaxInactiveInterval(1800);
                return ResponseEntity.ok(new LoginResponse("Login successful", loginUser.getLoginId()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid loginId or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    
    // 다른 컨트롤러 메서드들 추가 가능
}
