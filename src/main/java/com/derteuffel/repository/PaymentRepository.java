package com.derteuffel.repository;

import com.derteuffel.data.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 23/03/2019.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
