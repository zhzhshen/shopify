package com.thoughtworks.sid.gatewayService.repository;

import com.thoughtworks.sid.gatewayService.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
