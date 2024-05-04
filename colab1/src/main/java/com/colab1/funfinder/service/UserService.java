package com.colab1.funfinder.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.colab1.funfinder.dto.JoinRequest;
import com.colab1.funfinder.dto.LoginRequest;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	/**
	 * loginId 중복체크
	 * @param loginId
	 * @return true (중복)
	 */
	public boolean checkLoginIdDuplicate(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}
	/**
	 * nickname 중복체크
	 * @param nickname
	 * @return true (중복)
	 */
	public boolean checkNicknameDuplicate(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
	
	/**
	 * 회원가입 db 저장
	 * @param req
	 */
	public void join(JoinRequest req) {
		userRepository.save(req.toEntity());
	}
	/**
	 * 스프링 시큐리티를 사용할지 정해지면 사용
	 * 혹은 다른 암호화 로직을 추가하면 사용
	 */
//	public void join2(JoinRequest req) {
//		userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
//	}
	
	public User login(LoginRequest req) {
		Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());
		
		//같은 아이디가 없을 때
		if(optionalUser.isEmpty()) {
			System.out.println("id 없음");
			return null;
		}
		User user = optionalUser.get();
		
		//비밀번호 다름
		if(!user.getPassword().equals(req.getPassword())) {
			System.out.println("비번 다름");
			return null;
		}
		
		return user;
	}
	
	public User getLoginUserById(Long userId) {
		//파라미터 userId가 없으면 
		if(userId == null) return null;
		
		//파라미터 userId로 찾았을 때, 없으면
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) return null;
		
		return optionalUser.get();
	}
	
}
