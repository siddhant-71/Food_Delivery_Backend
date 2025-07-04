package com.siddhant.foodDelivery.Exceptions.PaymentExceptions;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
