package com.colab1.funfinder.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;

    @Column
    private String title;

    @Column
    private String userId; 

    // 필요에 따라 다른 필드들에도 @Column 어노테이션을 추가

    @Column
    private Date regDt; // 필드명 camelCase로 변경

    @Column
    private Date updDt; // 필드명 camelCase로 변경

    // getter와 setter 등 생략
}
