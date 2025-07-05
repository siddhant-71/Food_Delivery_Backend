package com.siddhant.foodDelivery.DTOs;

import com.siddhant.foodDelivery.Enums.PaymentMode;
import com.siddhant.foodDelivery.Enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private PaymentStatus status;
    private PaymentMode paymentMode;
    private double amount;
    private String message;
    private LocalDateTime timeStamp;
}
