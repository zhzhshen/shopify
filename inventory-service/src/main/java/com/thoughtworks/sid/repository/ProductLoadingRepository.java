package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.ProductLoading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLoadingRepository extends JpaRepository<ProductLoading, Long> {
    List<ProductLoading> getAllByStoreIdAndProductId(Long storeId, Long productId);
    ProductLoading getByIdAndStoreIdAndProductId(Long id, Long storeId, Long productId);
}
