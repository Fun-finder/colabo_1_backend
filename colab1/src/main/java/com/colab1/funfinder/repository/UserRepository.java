package com.colab1.funfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colab1.funfinder.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    Optional<User> findByLoginId(String loginId);
    List<User> findAll();
}
