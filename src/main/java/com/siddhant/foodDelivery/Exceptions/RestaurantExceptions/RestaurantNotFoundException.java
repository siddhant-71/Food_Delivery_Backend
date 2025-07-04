package com.siddhant.foodDelivery.Exceptions.RestaurantExceptions;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
