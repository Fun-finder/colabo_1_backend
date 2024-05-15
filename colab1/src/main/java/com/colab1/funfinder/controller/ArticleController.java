package com.colab1.funfinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession; 
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleSvc;
    
	@GetMapping("/{userId}")
	public ResponseEntity<Article> getArticle(@PathVariable("userId") String userId , HttpServletRequest req) {
		
		HttpSession session = req.getSession(true);
		String sessionUserId = (String) session.getAttribute("userId");
		
		//세션에 들어있는 로그인 정보와 다를 경우
		if(!userId.equals(sessionUserId)) return null;
		
		return new ResponseEntity<>(articleSvc.getArticle(userId), HttpStatus.OK);
	}
}
