package com.jex.official.repository;

import com.jex.official.entity.db.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select u from Order u")
    List<Order> findAllOrder(Pageable pageable);

    Optional<Order> findOneByOrderId(String orderId);

}
