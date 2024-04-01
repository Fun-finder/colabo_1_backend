package com.colab1.funfinder.dto;

import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.entity.UserRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
	@NotBlank(message = "로그인 아이디가 비어있습니다.")
	private String loginId;
	
	@NotBlank(message = "비밀번호가 비어있습니다.")
	private String password;
	private String passwordChk;
	
	@NotBlank(message="닉네임이 비어있습니다.")
	private String nickname;
	
	//비밀번호 암호화 없이
	public User toEntity() {
		return User.builder()
				.loginId(this.loginId)
				.password(this.password)
				.nickname(this.nickname)
				.role(UserRole.USER)
				.build();
	}
	//비밀번호 암호화
	public User toEntity(String encodedPassword) {
		return User.builder()
				.loginId(this.loginId)
				.password(encodedPassword)
				.nickname(this.nickname)
				.role(UserRole.USER)
				.build();
	}

}

