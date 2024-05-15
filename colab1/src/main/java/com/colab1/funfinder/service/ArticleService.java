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
     * userId로 article 가져오기
     * @param userId
     * @return
     */
	public Article getArticle(String userId) {
		List<Article> articleList = articleRepository.findByUserId(userId);
		return articleList.isEmpty() ? null : articleList.get(0);
	}

	/**
	 * userId로 articleList 가져오기
	 * @param user
	 * @return
	 */
	public List<Article> getArticleList(User user) {
		String userId = user.getLoginId();
		List<Article> articleList = articleRepository.findByUserId(userId);
		return articleList;
	}
	

}
