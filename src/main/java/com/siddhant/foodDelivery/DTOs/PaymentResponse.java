package com.siddhant.foodDelivery.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.type.ErrorType;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private String status;
    private String paymentMode;
    private double amount;
    private String message;
    private LocalDateTime timeStamp;
}
