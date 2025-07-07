package com.siddhant.foodDelivery.Controllers;

import com.siddhant.foodDelivery.Entities.Review;
import com.siddhant.foodDelivery.Service.Implementations.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;

    @PostMapping("/addReview")
    public void addReview(@RequestBody Review review){
        reviewService.addReview(review);
    }
    @PutMapping("/update/{id}")
    public void update(@RequestBody Review review , @PathVariable("id")long id){
        reviewService.updateReview(id,review);
    }
    @GetMapping("/")
    public List<Review> all(){
        return reviewService.getAllReviews();
    }
    @GetMapping("/{id}")
    public Review oneWithId(@PathVariable("id") long id){
        return reviewService.getReviewById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") long id){
        reviewService.deleteReview(id);
    }
}
