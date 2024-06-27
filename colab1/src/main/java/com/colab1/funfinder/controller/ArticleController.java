package com.colab1.funfinder.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.colab1.funfinder.config.JwtTokenProvider;
import com.colab1.funfinder.dto.TokenValidation;
import com.colab1.funfinder.entity.Article;
import com.colab1.funfinder.service.ArticleService;

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
	public ResponseEntity<?> getArticleList(@PathVariable("loginId") String loginId ,@RequestParam(value="page", defaultValue="-1") int page , @RequestHeader("Authorization") String accessToken) {
		TokenValidation tVal = getTokenValidation(accessToken, loginId);
		if(!tVal.getIsValid()) return tVal.getResEntity();
	
		int pageSize = 16;
		if(page == -1) {
			List<Article> articleList = articleSvc.getArticleList(loginId);
			return ResponseEntity.status(HttpStatus.OK).body(articleList);
		}
		List<Article> articleList = articleSvc.getArticleByPage(loginId, page, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(articleList);
	}
	
	@GetMapping("/{loginId}/{articleId}")
	public ResponseEntity<?> getArticle(@PathVariable("loginId") String loginId , @PathVariable("articleId") int articleId, @RequestHeader("Authorization") String accessToken) {
		
		TokenValidation tVal = getTokenValidation(accessToken, loginId);
		if(!tVal.getIsValid()) return tVal.getResEntity();
		
		Article article = articleSvc.getArticleByLoginIdAndArticleId(loginId, articleId);
		return ResponseEntity.status(HttpStatus.OK).body(article);
	}
	
	
	private TokenValidation getTokenValidation(String accessToken, String loginId) {
		//토큰 형식인지 체크
		if(accessToken == null || !accessToken.startsWith("Bearer ")) {
			return new TokenValidation("토큰이 없습니다.");
		}
		
		accessToken = accessToken.substring(7); // Remove "Bearer " prefix
		//여기에 refreshToken도 체크하는 로직을 넣을 생각
        if (!jwtTokenProvider.validateAccessToken(accessToken)) {
        	return new TokenValidation("유효하지 않은 토큰입니다.");
        }
        
        String loginIdByToken = jwtTokenProvider.getLoginIdFromAccessToken(accessToken);
        
		//로그인 안했을 경우
		if(loginIdByToken == null) {
			return new TokenValidation("로그인 정보가 없는 토큰입니다.");
		}
		
		//로그인 정보와 조회하려는 정보가 다를 경우
		if (!loginId.equals(loginIdByToken)) {
			return new TokenValidation("권한이 없습니다.");
		}
        
		//토큰이 올바르고 로그인정보가 같은 경우
        return new TokenValidation(loginId, true);
	}
	
}
