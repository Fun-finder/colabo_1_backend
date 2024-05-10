package com.colab1.funfinder.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.colab1.funfinder.dto.JoinRequest;
import com.colab1.funfinder.dto.LoginRequest;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"*"})
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) throws Exception {
        Optional<User> optionalUser = Optional.ofNullable(userService.login(request));
        if(optionalUser.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        User loginUser = optionalUser.get();
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", loginUser.getId());
        session.setMaxInactiveInterval(1800);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@Valid @RequestBody JoinRequest request) throws Exception {
        return ResponseEntity.ok(null);
    }


}
