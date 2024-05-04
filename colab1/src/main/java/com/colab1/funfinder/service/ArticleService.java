package com.colab1.funfinder.service;

import org.springframework.stereotype.Service;

import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.repository.ArticleRepository;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	public Article getArticle(User user) {
		String userId = user.getLoginId();
	}
}
