package com.siddhant.foodDelivery.DTOs;

import com.siddhant.foodDelivery.Enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private long addressId;
    private long restaurantId;
    private long userId;
    private PaymentMode paymentMode;
    private double totalAmount;
}
