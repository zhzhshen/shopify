package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    List<ProductPrice> findProductPricesByProductId(Long productId);

    @Query(value = "Select * from Product_Price where product_Id = :productId and effected_at <= CURRENT_DATE order by effected_at DESC", nativeQuery = true)
    List<ProductPrice> findAvailableProductPrice(@Param("productId") Long productId);
}
