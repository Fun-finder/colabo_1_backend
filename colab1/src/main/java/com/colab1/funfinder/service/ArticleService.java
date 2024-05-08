package com.colab1.funfinder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.ArticleRepository;
import com.colab1.funfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	/**
	 * loginId로 articleList 가져오기
	 * @param user
	 * @return
	 */
	public List<Article> getArticleList(User user) {
		String userId = user.getLoginId();
		List<Article> articleList = articleRepository.findByUserId(userId);
		return articleList;
	}
	
}
