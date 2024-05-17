package com.colab1.funfinder.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.colab1.funfinder.dto.JoinRequest;
// import com.colab1.funfinder.dto.LoginResponse;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User authenticate(String loginId, String password) {
    	// 사용자 인증 로직을 구현하고, 유효한 경우 토큰을 반환합니다.
    	// 여기에서는 간단하게 loginId와 password가 일치하는 경우에만 토큰을 발급하도록 하겠습니다.
    	Optional<User> optionalUser = userRepository.findByLoginId(loginId);
    	if (optionalUser.isPresent()) {
    		User user = optionalUser.get();
    		if (user.getPassword().equals(password)) {
    			return user;
    		}
    	}
    	return null; // 인증 실패 시 null을 반환합니다.
    }

    public void registerUser(JoinRequest request) {
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        // 나머지 필드 설정...

        userRepository.save(user);
    }

    // 토큰 생성 로직을 여기에 구현합니다.
    private String generateToken(User user) {
    	
        // 실제 토큰 생성 로직을 구현해야 합니다.
    	return user.getLoginId();
    }
}
