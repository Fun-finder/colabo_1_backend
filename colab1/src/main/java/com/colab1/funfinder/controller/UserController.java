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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

		private final UserService userService;
		/**
		 * 로그인 요청 
		 * @param request
		 * @return 로그인 아이디 (long)
		 * @throws Exception
		 */
		@PostMapping("/login")
		public ResponseEntity login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) throws Exception{
			Optional<User> optionalUser = Optional.ofNullable(userService.login(request));
			System.out.println("=====================");
			System.out.println("login id : "+request.getLoginId());
			System.out.println("login pw : "+request.getPassword());
			System.out.println("optionalUser : "+optionalUser);
			System.out.println("=====================");
			if(optionalUser.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
			
			User loginUser = optionalUser.get();
			// 세션을 생성하기 전에 기존의 세션 파기
			httpServletRequest.getSession().invalidate();
			HttpSession session = httpServletRequest.getSession(true);
	        // 세션에 userId를 넣어줌
	        session.setAttribute("userId", loginUser.getId());
	        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
	        //로그인 화면에 뿌려주고 싶은게 있으면
	        //화면에 뿌리는 DTO를 만들어서 값을 넣어주자
			return new ResponseEntity(HttpStatus.OK);
		}
		
		@PostMapping("/join")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<String> join(@Valid @RequestBody JoinRequest request) throws Exception{
			return ResponseEntity.ok(null);
		}
		
}
