package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    //Crud
    Review addReview(Review review);
    void updateReview(long ReviewId, Review review);
    void deleteReview(long ReviewId);
    Review getReviewById(long ReviewId);
    List<Review> getAllReviews();


    //SEARCH
    List<Review> searchReviewsByRating(int rating);
    List<Review> searchReviewsByUserId(long UserId);
    Review searchReviewsByOrderId(long orderId);
    List<Review> searchReviewsByRestaurantId(long restaurantId);
    List<Review> searchReviewsByRestaurantName(String name);
    List<Review> searchReviewsByDateRange(LocalDateTime start, LocalDateTime end);


    long countReviewsForRestaurant(Long restaurantId);
    long countReviewsForRestaurantName(String name);

    //ADMIN
    void approveReview(long reviewId);
    void rejectReview(long reviewId);
    List<Review> getAllPendingReviews();
    List<Review> getAllApprovedReviews();
    List<Review> getAllRejectedReviews();
}
