package com.jex.official.repository;

import com.jex.official.entity.db.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "select * from payment limit 1", nativeQuery = true)
    Payment findOnePayment();
}
