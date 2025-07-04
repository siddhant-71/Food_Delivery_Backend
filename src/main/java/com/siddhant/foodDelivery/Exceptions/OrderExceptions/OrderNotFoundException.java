package com.siddhant.foodDelivery.Exceptions.OrderExceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
