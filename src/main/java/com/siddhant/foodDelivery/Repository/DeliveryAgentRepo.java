package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryAgentRepo extends JpaRepository<DeliveryAgent, Long> {
    Optional<DeliveryAgent> findByPhone(String phone);
    List<DeliveryAgent> findAllByRatingBetween(double start, double end);
    boolean existsByPhone(String phone);
    List<DeliveryAgent> findAllByName(String name);
    List<DeliveryAgent> findAllByRating(double rating);
    long countAllByAvailability(boolean availability);
}
