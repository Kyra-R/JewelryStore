package com.rschir.prac.repositories;

import com.rschir.prac.model.CartJewelry;
import com.rschir.prac.model.CartJewelryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CartJewelryRepository extends JpaRepository<CartJewelry, CartJewelryId> {
    List<CartJewelry> findAllByClientId(Long clientId);
    void deleteAllByClientId(Long clientId);
    CartJewelry findByClientIdAndJewelryId(Long clientId, Long productId);
}
