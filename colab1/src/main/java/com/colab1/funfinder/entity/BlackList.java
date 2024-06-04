package com.colab1.funfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="colab_black_list")
public class BlackList {
	@Id
	@Column(name="black_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blackId;
	@Column(name="invalid_refresh_token")
	private String invalidRefreshToken;
	
	public BlackList(String invalidRefreshToken) {
		this.invalidRefreshToken = invalidRefreshToken;
	}
}
