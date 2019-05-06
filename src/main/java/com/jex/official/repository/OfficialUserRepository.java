package com.jex.official.repository;

import com.jex.official.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfficialUserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
