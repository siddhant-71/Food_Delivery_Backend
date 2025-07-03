package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepo extends JpaRepository<Dish, Long> {
    List<Dish> findByName(String name);
    List<Dish> findAllByPriceBetween(double start, double end);
    List<Dish> findAllByMenuId(long menuId);
    List<Dish> findAllByMenuRestaurantId(long restaurantId);
    List<Dish> findAllByRatingBetween(double start, double end);
    List<Dish> findAllByRating(double rating);
    List<Dish> findAllByMenuCategory(String category);
}
