package com.thoughtworks.sid.repository;

import com.thoughtworks.sid.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> getAllByOwner(String owner);
    Store getByOwnerAndId(String owner, Long id);
}
