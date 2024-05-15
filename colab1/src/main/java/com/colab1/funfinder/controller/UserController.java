package com.colab1.funfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colab1.funfinder.dto.LoginRequest;
import com.colab1.funfinder.dto.LoginResponse;
import com.colab1.funfinder.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/user/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        try {
            // String token = userService.authenticate(request.getLoginId(), request.getPassword());
            // if (token != null) {
            //     httpServletRequest.getSession(true).setAttribute("token", token);
            //     httpServletRequest.getSession().setMaxInactiveInterval(1800);
            //     return ResponseEntity.ok(new LoginResponse("Login successful", token));
            // } else {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid loginId or password");
            // }
            return ResponseEntity.status(200).body("response ok!!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    
    // 다른 컨트롤러 메서드들 추가 가능
}
