package com.jex.official.repository;

import com.jex.official.entity.db.SubscribeReply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscribeReplyRepository extends JpaRepository<SubscribeReply, Integer> {

    @Query("select s from SubscribeReply s")
    List<SubscribeReply> findAllSubscribeReply(Pageable pageable);

    Optional<SubscribeReply> findByOfficialId(int officialId);
}
