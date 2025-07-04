package com.siddhant.foodDelivery.Exceptions.MenuExceptions;

public class MenuNotFoundException extends RuntimeException{
    public MenuNotFoundException(String message) {
        super(message);
    }
}
