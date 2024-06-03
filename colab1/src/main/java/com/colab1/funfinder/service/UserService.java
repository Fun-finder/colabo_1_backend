package com.colab1.funfinder.service;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.colab1.funfinder.dto.JoinRequest;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.UserRepository;
import com.colab1.funfinder.config.JwtTokenProvider;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // PasswordEncoder 추가

    public Boolean authenticate(String loginId, String password) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        //db 존재 여부 체크
        if (!optionalUser.isPresent()) {
        	return false;
        }

        //password 체크
        if (!passwordEncoder.matches(password, optionalUser.get().getPassword())) { // 암호화된 비밀번호 비교
            return false;
        }

        return true;
    }

    public void registerUser(JoinRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("User with loginId already exists");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("User with nickname already exists");
        }
        
        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if (!request.getPassword().equals(request.getPasswordChk())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntity(encodedPassword);
        
        userRepository.save(user);
    }

    public Map<String, Object> getProfileData(String loginId) {
        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with loginId: " + loginId));

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("loginId", user.getLoginId()); 
        profileData.put("email", user.getEmail()); 
        profileData.put("nickname", user.getNickname()); 
        profileData.put("role", user.getRole()); 
        return profileData;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with loginId: " + loginId));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
        
        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), authorities);
    }
}
