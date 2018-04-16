package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
