package com.jex.official.repository;

import com.jex.official.entity.db.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a")
    List<Activity> findAllActivity(Pageable pageable);

    Optional<Activity> findOneById(int id);

}
