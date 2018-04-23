package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
