package com.colab1.funfinder.entity;

import java.util.Date;

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
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="colab_article")
public class Article {
    @Id
    @Column(name="article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    @Column(name="login_id")
    private String loginId; 
    @Column
    private String title;
    @Column(name="reg_dt")
    private Date regDt; // 필드명 camelCase로 변경
    @Column(name="upd_dt")
    private Date updDt; // 필드명 camelCase로 변경
    @Column(name="emotion_cd")
    private Emotion emotionCd;
    @Column(name="content")
    private String content;
    @Column(name="reason")
    private String reason;
    @Column(name="pic_cnt")
    private int picCnt;
}
