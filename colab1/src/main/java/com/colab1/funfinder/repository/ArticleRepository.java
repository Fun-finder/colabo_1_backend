package com.colab1.funfinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colab1.funfinder.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
	
	boolean existsByArticleId(int articleId);
	Optional<Article> findByArticleId(int articleId);
	Optional<Article> findByLoginId(String userId);
}
