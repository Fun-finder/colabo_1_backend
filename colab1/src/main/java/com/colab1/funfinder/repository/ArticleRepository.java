package com.colab1.funfinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colab1.funfinder.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    
    /**
     * userId로 Article 목록 조회
     * @param userId 사용자 ID
     * @return 해당 사용자의 Article 목록
     */
    List<Article> findByLoginId(String loginId);
}
