package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> getAllByStoreIdAndProductId(Long storeId, Long productId);
    Inventory getByIdAndStoreIdAndProductId(Long id, Long storeId, Long productId);
    Inventory getByStoreIdAndProductIdOrderByIdDesc(Long storeId, Long productId);
}
