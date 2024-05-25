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
    private final JwtTokenProvider jwtTokenProvider;

    public String authenticate(String loginId, String password) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return jwtTokenProvider.createToken(loginId);
            }
        }
        return null; 
    }

    public void registerUser(JoinRequest request) {
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        // 나머지 필드 설정...

        userRepository.save(user);
    }

    public Map<String, Object> getProfileData(String loginId) {
        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        User user = userOptional.orElse(null); // orElseThrow()를 사용하여 예외를 던질 수도 있습니다.

        Map<String, Object> profileData = new HashMap<>();
        if (user != null) {
            profileData.put("loginId", user.getLoginId()); 
            profileData.put("password", user.getPassword()); 
            profileData.put("nickname", user.getNickname()); 
            profileData.put("role", user.getRole()); 
        }
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
