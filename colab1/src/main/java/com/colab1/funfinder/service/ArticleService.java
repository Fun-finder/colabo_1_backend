package com.colab1.funfinder.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.ArticleRepository;

import lombok.RequiredArgsConstructor; // 롬복의 RequiredArgsConstructor 추가

@Service
@RequiredArgsConstructor // 롬복의 RequiredArgsConstructor 사용
public class ArticleService {
	private final ArticleRepository articleRepository;
	
    /**
     * ArticleId로 article 가져오기
     * @param userId
     * @return
     */
	public Article getArticle(int ArticleId) {
		Article article = articleRepository.findByArticleId(ArticleId);
		return article;
	}

	/**
	 * ArticleId로 article 가져오기
	 * @param userId
	 * @return
	 */
	public Article getArticleByLoginIdAndArticleId(String loginId, int ArticleId) {
		Article article = articleRepository.findByLoginIdAndArticleId(loginId, ArticleId);
		return article;
	}

	/**
	 * userId로 articleList 가져오기
	 * @param user
	 * @return List<Article>
	 */
	public List<Article> getArticleList(User user) {
		String userId = user.getLoginId();
		List<Article> articleList = articleRepository.findByLoginId(userId);
		return articleList;
	}
	/**
	 * userId로 articleList 가져오기
	 * @param user
	 * @return List<Article>
	 */
	public List<Article> getArticleList(String loginId) {
		List<Article> articleList = articleRepository.findByLoginId(loginId);
		return articleList;
	}

}
