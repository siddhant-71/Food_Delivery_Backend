package com.siddhant.foodDelivery.Exceptions.DishExceptions;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String message) {
        super(message);
    }
}
