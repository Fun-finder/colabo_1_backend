package com.colab1.funfinder.dto;

public class LoginResponse {
    private String message;
    private String token;

    // 생성자
    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // getter 및 setter 메소드
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
