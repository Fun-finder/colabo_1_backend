package com.colab1.funfinder.repository;

import java.util.Optional;

import com.colab1.funfinder.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	boolean existsByCommentId(int commentId);
	Optional<Comment> findByCommentId(int commentId);
	Optional<Comment> findByUserId(String userId);
}
