package com.siddhant.foodDelivery.Exceptions.AddressExceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String message) {
        super(message);
    }
}
