package com.colab1.funfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colab1.funfinder.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByLoginId(String loginId);
	boolean existsByNickname(String nickname);
	Optional<User> findByLoginId(String loginId);
	List<User> findAll();

}
