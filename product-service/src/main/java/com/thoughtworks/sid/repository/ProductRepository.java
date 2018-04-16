package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
