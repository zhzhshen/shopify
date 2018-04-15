package com.thoughtworks.sid.productService.repository;

import com.thoughtworks.sid.productService.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
