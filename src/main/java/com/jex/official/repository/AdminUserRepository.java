package com.jex.official.repository;

import com.jex.official.entity.db.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

    Optional<AdminUser> findByUsernameAndPassword(String username, String password);
}
