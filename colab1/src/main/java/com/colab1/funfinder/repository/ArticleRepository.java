package com.colab1.funfinder.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
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
    /**
     * userId로 Article 목록 조회
     * @param userId 사용자 ID, pageable 페이징을 위한 객체
     * @return 해당 사용자의 Article 목록
     */
    List<Article> findByLoginId(String loginId, Pageable pageable);

    /**
     * articleId로 article 조회
     * @param articleId
     * @return
     */
	Article findByArticleId(int articleId);
	/**
	 * 로그인한 사용자와 article아이디로 조회
	 * @param loginId
	 * @param articleId
	 * @return
	 */
	Article findByLoginIdAndArticleId(String loginId, int articleId);
	
	
}
