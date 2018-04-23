package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.ProductUnloading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUnloadingRepository extends JpaRepository<ProductUnloading, Long> {
    List<ProductUnloading> getAllByStoreIdAndProductId(Long storeId, Long productId);
    ProductUnloading getByIdAndStoreIdAndProductId(Long id, Long storeId, Long productId);
}
