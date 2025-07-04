package com.siddhant.foodDelivery.Exceptions.CartExceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
