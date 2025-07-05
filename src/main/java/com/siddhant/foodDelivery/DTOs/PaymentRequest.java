package com.siddhant.foodDelivery.DTOs;


import com.siddhant.foodDelivery.Enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private double amount;
    private PaymentMode paymentMethod;
    private String billingName;
    private String billingEmail;
    private String billingPhone;
}
