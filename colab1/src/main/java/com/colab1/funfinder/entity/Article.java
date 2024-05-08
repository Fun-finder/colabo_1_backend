package com.colab1.funfinder.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="colab_article")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="article_id" , nullable= false)
	private int article_id;
	private String title;
	private String user_id;
	@Enumerated(EnumType.STRING)
	private Emotion emotion_cd;
	private String reason;
	private List<Picture> pictures;
	private String content;
	private Date reg_dt;
	private Date upd_dt;
}
