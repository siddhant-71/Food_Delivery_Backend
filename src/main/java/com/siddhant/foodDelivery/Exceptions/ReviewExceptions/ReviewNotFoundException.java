package com.siddhant.foodDelivery.Exceptions.ReviewExceptions;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
