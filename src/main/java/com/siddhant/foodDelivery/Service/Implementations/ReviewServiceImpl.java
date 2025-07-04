package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Entities.Restaurant;
import com.siddhant.foodDelivery.Entities.Review;
import com.siddhant.foodDelivery.Exceptions.RestaurantExceptions.RestaurantNotFoundException;
import com.siddhant.foodDelivery.Exceptions.ReviewExceptions.ReviewNotFoundException;
import com.siddhant.foodDelivery.Repository.OrderRepo;
import com.siddhant.foodDelivery.Repository.RestaurantRepo;
import com.siddhant.foodDelivery.Repository.ReviewRepo;
import com.siddhant.foodDelivery.Service.Interfaces.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final static Logger logger= LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    @Override
    public Review addReview(Review review) {
        reviewRepo.save(review);
        logger.info("Review added successfully");
        return review;
    }

    @Override
    public void updateReview(long ReviewId, Review review) {
        Review reviewToUpdate=reviewRepo.findById(ReviewId).orElseThrow(()->new ReviewNotFoundException("Review with id "+ReviewId+" not found"));
        if(review.getComment()!=null)reviewToUpdate.setComment(review.getComment());
        if(review.getRating()!=null)reviewToUpdate.setRating(review.getRating());
        reviewToUpdate.setReviewDate(LocalDateTime.now());
        reviewToUpdate.setStatus("PENDING");
        reviewRepo.save(reviewToUpdate);
        logger.info("Review with id {} updated successfully",ReviewId);
    }

    @Override
    public void deleteReview(long ReviewId) {
        Review reviewToDelete=reviewRepo.findById(ReviewId).orElseThrow(()->new ReviewNotFoundException("Review with id "+ReviewId+" not found"));
        reviewRepo.delete(reviewToDelete);
        logger.info("Review with id {} deleted successfully",ReviewId);
    }

    @Override
    public Review getReviewById(long ReviewId) {
        return reviewRepo.findById(ReviewId).orElseThrow(()->new ReviewNotFoundException("Review with id "+ReviewId+" not found"));
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    @Override
    public List<Review> searchReviewsByRating(int rating) {
        return reviewRepo.findAllByRating(rating);
    }

    @Override
    public List<Review> searchReviewsByUserId(long UserId) {
        return reviewRepo.findAllByUserId(UserId);
    }

    @Override
    public List<Review> searchReviewsByOrderId(long orderId) {
        return reviewRepo.findAllByOrderId(orderId);
    }

    @Override
    public List<Review> searchReviewsByRestaurantId(long restaurantId) {
        List<Order>orders=orderRepo.findAllByRestaurantId(restaurantId);
        if(orders==null || orders.isEmpty()){
            logger.warn("No reviews found for restaurant with id {}",restaurantId);
            return List.of();
        }
        List<Review>ans=new ArrayList<>();
        for(Order order:orders){
            ans.addAll(reviewRepo.findAllByOrderId(order.getId()));
        }
        if(ans.isEmpty()){
            logger.warn("No reviews found for restaurant with id {}",restaurantId);
            return List.of();
        }else{
            logger.info("Found {} reviews for restaurant with id {}",ans.size(),restaurantId);
            return ans;
        }
    }

    @Override
    public List<Review> searchReviewsByRestaurantName(String name) {
        return reviewRepo.findAllByDishMenuRestaurantName(name);
    }

    @Override
    public List<Review> searchReviewsByDateRange(LocalDateTime start, LocalDateTime end) {
        return reviewRepo.findAllByReviewDateBetween(start,end);
    }

    @Override
    public long countReviewsForRestaurant(Long restaurantId) {
        return reviewRepo.countByDishMenuRestaurantId(restaurantId);
    }

    @Override
    public long countReviewsForRestaurantName(String name) {
        return reviewRepo.countByDishMenuRestaurantName(name);
    }

    @Override
    @Transactional
    public void approveReview(long reviewId) {
        Review review=reviewRepo.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Review with review id "+reviewId+" not found"));
        review.setStatus("APPROVED");
        logger.info("Review with id {} approved successfully",reviewId);
        reviewRepo.save(review);
        return ;
    }

    @Override
    public void rejectReview(long reviewId) {
        Review review=reviewRepo.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Review with review id "+reviewId+" not found"));
        review.setStatus("REJECTED");
        logger.info("Review with id {} rejected successfully",reviewId);
        reviewRepo.save(review);
    }

    @Override
    public List<Review> getAllPendingReviews() {
        return reviewRepo.findAllByStatus("PENDING");
    }

    @Override
    public List<Review> getAllApprovedReviews() {
        return reviewRepo.findAllByStatus("APPROVED");
    }

    @Override
    public List<Review> getAllRejectedReviews() {
        return reviewRepo.findAllByStatus("REJECTED");
    }
}
