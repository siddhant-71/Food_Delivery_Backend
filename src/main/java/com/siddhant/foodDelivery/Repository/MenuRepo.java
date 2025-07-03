package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(long restaurantId);
    List<Menu> findByRestaurantName(String name);
    Optional<Menu> findByCategory(String category);
    Optional<Menu> findByDishesId(long dishId);
    Optional<Menu> findByDishesName(String dishName);
}
