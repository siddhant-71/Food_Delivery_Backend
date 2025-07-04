package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);
    boolean existsByName(String name);
    List<Restaurant> findAllByName(String name);
    List<Restaurant> findAllByPincode(int pincode);
    List<Restaurant> findAllByCity(String city);
    List<Restaurant> findAllByArea(String area);
    List<Restaurant> findAllByState(String state);
    List<Restaurant> findAllByRatingBetween(double start, double end);
}
