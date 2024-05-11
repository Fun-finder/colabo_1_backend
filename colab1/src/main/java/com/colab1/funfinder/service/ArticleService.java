package com.colab1.funfinder.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.ArticleRepository;
import com.colab1.funfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepo;
	private final UserRepository userRepo;

	/**
	 * User 객체를 인자로 받을 경우
	 * @param User
	 * @return Article
	 */
	public Article getArticle(User user) {
		String userId = user.getLoginId();
		Optional<Article> article = articleRepo.findByLoginId(userId);
		
		if(article.isEmpty()) return null;
		return article.get();
	}
	/**
	 * User 객체를 인자로 받을 경우
	 * @param User
	 * @return Article
	 */
	public Article getArticle(String userId) {
		Optional<Article> article = articleRepo.findByLoginId(userId);
		
		if(article.isEmpty()) return null;
		return article.get();
	}
	
	/**
	 * article_id를 인자로 받을 경우
	 * @param User
	 * @return Article
	 */
	public Article getArticle(int articleId) {
		Optional<Article> article = articleRepo.findByArticleId(articleId);
		if(article.isEmpty()) return null;
		return article.get();
	}


}
