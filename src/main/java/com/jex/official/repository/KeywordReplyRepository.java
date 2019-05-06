package com.jex.official.repository;

import com.jex.official.entity.db.KeywordReply;
import com.jex.official.entity.db.Official;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface KeywordReplyRepository extends JpaRepository<KeywordReply, Integer>, JpaSpecificationExecutor<KeywordReply> {
    Optional<KeywordReply> findOneByOfficialAndKeyword(Official official, String keyword);
}
