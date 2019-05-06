package com.jex.official.repository;

import com.jex.official.entity.db.Official;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficialRepository extends JpaRepository<Official, Integer> {
    Optional<Official> findOneByAccount(String account);
    Optional<Official> findOneByAppId(String appId);
}
