package com.siddhant.foodDelivery.Exceptions.UserException;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message) {
        throw new RuntimeException(message);
    }
}
