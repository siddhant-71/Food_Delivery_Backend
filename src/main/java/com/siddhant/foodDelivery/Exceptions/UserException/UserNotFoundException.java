package com.siddhant.foodDelivery.Exceptions.UserException;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
