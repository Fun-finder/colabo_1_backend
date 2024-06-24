package com.colab1.funfinder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.colab1.funfinder.config.JwtTokenProvider;
import com.colab1.funfinder.dto.JoinRequest;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Boolean authenticate(String loginId, String password) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if (!optionalUser.isPresent()) {
            return false;
        }
        if (!passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return false;
        }
        return true;
    }

    public Map<String, String> generateTokens(String loginId) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with loginId: " + loginId));

        String accessToken = jwtTokenProvider.createAccessToken(user.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getLoginId());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    public Map<String, String> refreshToken(String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String loginId = jwtTokenProvider.getLoginIdFromRefreshToken(refreshToken);
            return generateTokens(loginId);
        }
        throw new IllegalArgumentException("Invalid refresh token");
    }

    public void registerUser(JoinRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("User with loginId already exists");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("User with nickname already exists");
        }
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

        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), List.of());
    }
}
