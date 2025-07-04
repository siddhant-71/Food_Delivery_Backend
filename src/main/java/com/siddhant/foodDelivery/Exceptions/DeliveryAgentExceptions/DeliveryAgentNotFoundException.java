package com.siddhant.foodDelivery.Exceptions.DeliveryAgentExceptions;

public class DeliveryAgentNotFoundException extends RuntimeException {
    public DeliveryAgentNotFoundException(String message) {
        super(message);
    }
}
