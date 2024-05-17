package com.colab1.funfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.service.ArticleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleSvc;
    
	@GetMapping("/{loginId}")
	public ResponseEntity<?> getArticleList(@PathVariable("loginId") String loginId , HttpServletRequest req) {
		
		HttpSession session = req.getSession(true);
		User sessionUser = (User) session.getAttribute("loginUser");
		String sessionLoginId = sessionUser.getLoginId();
		
		if (!loginId.equals(sessionLoginId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
		
		List<Article> articleList = articleSvc.getArticleList(sessionUser);
		
		return ResponseEntity.status(HttpStatus.OK).body(articleList);
	}
	
	@GetMapping("/test")
	public ResponseEntity <Article> test(HttpServletRequest req){
		System.out.println("test");
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
