package com.colab1.funfinder.repository;

import com.colab1.funfinder.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByCommentId(int commentId);
    Optional<Comment> findByCommentId(int commentId);
    Optional<Comment> findByUserId(String userId);
}
