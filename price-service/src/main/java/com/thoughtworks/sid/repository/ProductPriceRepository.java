package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    List<ProductPrice> findProductPricesByProductId(Long productId);

    ProductPrice findByIdAndProductId(Long id, Long productId);
}
