package com.thoughtworks.sid.service;

import com.thoughtworks.sid.domain.Inventory;
import com.thoughtworks.sid.domain.ProductLoading;
import com.thoughtworks.sid.domain.ProductUnloading;
import com.thoughtworks.sid.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    public void loading(ProductLoading loading) {
        Inventory latestInventory = inventoryRepository.getByStoreIdAndProductIdOrderByIdDesc(loading.getStoreId(), loading.getProductId());
        Integer currentStock = latestInventory != null ? latestInventory.getStock() : 0;
        inventoryRepository.save(new Inventory(loading.getStoreId(), loading.getProductId(), loading.getStock() + currentStock, "loading", loading.getId()));
    }

    public void unloading(ProductUnloading unloading) {
        Inventory latestInventory = inventoryRepository.getByStoreIdAndProductIdOrderByIdDesc(unloading.getStoreId(), unloading.getProductId());
        Integer currentStock = latestInventory != null ? latestInventory.getStock() : 0;
        inventoryRepository.save(new Inventory(unloading.getStoreId(), unloading.getProductId(), currentStock - unloading.getStock(), "unloading", unloading.getId()));
    }
}
