package com.colab1.funfinder.dto;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	
	private String loginId;
	private String password;
	private LoginRequest lr;

	private void setLoginRequest (String loginId, String pw) {
		this.loginId = loginId;
		this.password = pw;
	}
	
	//유효성 체크
	private boolean validate(LoginRequest lr) {
		if(lr.getLoginId() == null || lr.getPassword() == null) {
			return false;
		}
		return true;
	}
	
}
