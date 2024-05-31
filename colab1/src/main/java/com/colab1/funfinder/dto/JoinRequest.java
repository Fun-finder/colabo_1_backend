package com.colab1.funfinder.dto;

import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.entity.UserRole;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JoinRequest {
    
    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String loginId;
    
    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    
    @NotBlank(message = "비밀번호 확인이 비어있습니다.")
    private String passwordChk;
    
    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;
    
    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }
    
    public User toEntity(String encodedPassword) {
        return User.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }
}
