package com.colab1.funfinder.entity;

import java.util.Date;
import java.util.List;

public class Article {
	private int article_id;
	private String title;
	private String user_id;
	private Emotion emotion_cd;
	private String reason;
	private List<Picture> pictures;
	private String content;
	private Date reg_dt;
	private Date upd_dt;
	
}
