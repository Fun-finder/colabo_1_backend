package com.colab1.funfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colab1.funfinder.config.JwtTokenProvider;
import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.entity.User;
import com.colab1.funfinder.service.ArticleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/article")
public class ArticleController {
    
	private final ArticleService articleSvc;
    private final JwtTokenProvider jwtTokenProvider;
    
    public ArticleController(ArticleService articleSvc, JwtTokenProvider jwtTokenProvider) {
        this.articleSvc = articleSvc;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
	@GetMapping("/{loginId}")
	public ResponseEntity<?> getArticleList(@PathVariable("loginId") String loginId , @RequestHeader("Authorization") String acessToken) {
		
		if(acessToken == null || !acessToken.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 없습니다.");
		}
		
		acessToken = acessToken.substring(7); // Remove "Bearer " prefix
		//여기에 refreshToken도 체크하는 로직을 넣을 생각
        if (!jwtTokenProvider.validateAccessToken(acessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
		
        String loginIdByToken = jwtTokenProvider.getLoginIdFromAccessToken(acessToken);
        
		//로그인 안했을 경우
		if(loginIdByToken == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 정보가 없는 토큰입니다.");
		
		//로그인 정보와 조회하려는 정보가 다를 경우
		if (!loginId.equals(loginIdByToken)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
		}
		
		List<Article> articleList = articleSvc.getArticleList(loginId);
		return ResponseEntity.status(HttpStatus.OK).body(articleList);
	}
	
	@GetMapping("/{loginId}/{articleId}")
	public ResponseEntity<?> getArticle(@PathVariable("loginId") String loginId , @PathVariable("articleId") int articleId, HttpServletRequest req) {
		
		HttpSession session = req.getSession(true);
		User sessionUser = (User) session.getAttribute("loginUser");
		//로그인 안했을 경우
		if(sessionUser == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

		String sessionLoginId = sessionUser.getLoginId();
		if (!loginId.equals(sessionLoginId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		
		Article article = articleSvc.getArticleByLoginIdAndArticleId(loginId, articleId);
		return ResponseEntity.status(HttpStatus.OK).body(article);
	}
	
}
