package com.jex.official.repository;

import com.jex.official.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAppIdAndOpenId(String appId, String openId);
}
