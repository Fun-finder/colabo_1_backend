package com.colab1.funfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="colab_user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="login_id")
	private String loginId;
	private String password;
	private String nickname;
	private UserRole role;

	public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
	public void setPassword(String password) {
        this.password = password;
    }

}
