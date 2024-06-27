package com.colab1.funfinder.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TokenValidation {
	
	private ResponseEntity<?> resEntity;
	private String loginId;
	private boolean isValid;
	

	public TokenValidation (String loginId, boolean valid) {
		this.resEntity= null;
		this.loginId= loginId;
		this.isValid = true;
	}

	public TokenValidation (ResponseEntity<?> resEntity) {
		this.resEntity= resEntity;
		this.loginId= "";
		this.isValid = false;
	}

	public TokenValidation (String errorMsg) {
		this.resEntity= ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMsg);
		this.loginId= "";
		this.isValid = false;
	}

	public void setResonseEntity(ResponseEntity<?> resEntity) {
		if(isValid) 
			this.resEntity = resEntity;
		else 
			this.resEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
	}
	
	public boolean getIsValid() {
		return isValid;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public ResponseEntity<?> getResEntity() {
		return resEntity;
	}
}
