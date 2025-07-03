package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(long userId);
    boolean existsByUserId(long userId);
    void deleteByUserId(long userId);
}
