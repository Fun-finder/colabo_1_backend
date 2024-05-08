package com.colab1.funfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colab1.funfinder.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
	
	boolean existsByArticleId(int articleId);
	
	Optional<Article> findByArticleId(int articleId);
	
	List<Article> findByUserId(String userId);
}
