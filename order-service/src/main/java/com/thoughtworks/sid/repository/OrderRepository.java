package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByCustomer(String owner);
    Order getByCustomerAndId(String owner, Long id);
}
