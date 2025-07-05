package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.DTOs.PaymentRequest;
import com.siddhant.foodDelivery.DTOs.PaymentResponse;
import com.siddhant.foodDelivery.Entities.Payment;
import com.siddhant.foodDelivery.Enums.PaymentStatus;

import java.util.List;

public interface PaymentService {
    Payment initiatePayment(long orderId,PaymentRequest paymentRequest);
    Payment getPaymentByPaymentId(long paymentId);
    Payment completePayment(long paymentId, PaymentResponse paymentResponse);


    //GET PAYMENT
    Payment getPaymentByOrderId(long orderId);
    Payment getPaymentByTransactionId(String transactionId);


    List<Payment> getAllPaymentsForUser(long UserId);
    Payment getPaymentForOrder(long OrderId);
    //Payment Status
    boolean isPaymentCompleted(long paymentId);
    void updatePaymentStatus(long paymentId, PaymentStatus status);
    PaymentStatus getPaymentStatus(long paymentId);
}
