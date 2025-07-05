package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Review;
import com.siddhant.foodDelivery.Enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findAllByReviewDate(LocalDateTime reviewDate);
    List<Review> findAllByReviewDateBetween(LocalDateTime start, LocalDateTime end);
    Optional<Review> findByOrderId(long orderId);
    List<Review> findAllByStatus(ReviewStatus status);
    List<Review> findAllByRating(int rating);
    List<Review> findAllByUserId(long userId);
    List<Review> findAllByDishMenuRestaurantId(long restaurantId);
    List<Review> findAllByDishMenuRestaurantName(String name);
    List<Review> findAllByDishName(String dishName);
    long countByDishMenuRestaurantName(String name);
    long countByDishMenuRestaurantId(long restaurantId);
}
