package com.colab1.funfinder.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int commentId;
    private String userId;
    private String comment;
    
    // Getter와 Setter는 생략되었습니다.
}
