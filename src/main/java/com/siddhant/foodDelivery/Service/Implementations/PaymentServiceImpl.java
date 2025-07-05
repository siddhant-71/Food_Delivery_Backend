package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.DTOs.PaymentRequest;
import com.siddhant.foodDelivery.DTOs.PaymentResponse;
import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Entities.Payment;
import com.siddhant.foodDelivery.Enums.PaymentMode;
import com.siddhant.foodDelivery.Enums.PaymentStatus;
import com.siddhant.foodDelivery.Exceptions.OrderExceptions.OrderNotFoundException;
import com.siddhant.foodDelivery.Exceptions.PaymentExceptions.PaymentNotFoundException;
import com.siddhant.foodDelivery.Repository.OrderRepo;
import com.siddhant.foodDelivery.Repository.PaymentRepo;
import com.siddhant.foodDelivery.Repository.UserRepo;
import com.siddhant.foodDelivery.Service.Interfaces.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    final static Logger logger= LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Payment initiatePayment(long orderId, PaymentRequest paymentRequest) {
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
        Payment payment=new Payment();
        payment.setPaymentMode(paymentRequest.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(paymentRequest.getAmount());
        payment.setOrder(order);
        paymentRepo.save(payment);
        order.setPayment(payment);
        orderRepo.save(order);
        logger.info("Payment initiated for order with id {} and amount {}",orderId,paymentRequest.getAmount());
        return payment;
    }

    @Override
    public Payment getPaymentByPaymentId(long paymentId) {
        return paymentRepo.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment with id "+paymentId+" not found"));
    }

    @Override
    public Payment completePayment(long paymentId, PaymentResponse paymentResponse) {
        Payment payment=paymentRepo.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment with id "+paymentId+" not found"));
        if(paymentResponse.getTransactionId()==null) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepo.save(payment);
            logger.warn("Transaction failed for payment with id {}",paymentId);
            return payment;
        }
        payment.setTransactionId(paymentResponse.getTransactionId());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepo.save(payment);
        logger.info("Payment with id {} completed successfully",paymentId);
        return payment;
    }

    @Override
    public Payment getPaymentByOrderId(long orderId) {
        return paymentRepo.findByOrderId(orderId).orElseThrow(()->new PaymentNotFoundException("Payment with order id "+orderId+" not found"));
    }

    @Override
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepo.findByTransactionId(transactionId).orElseThrow(()->new PaymentNotFoundException("Payment with transaction id "+transactionId+" not found"));
    }

    @Override
    public List<Payment> getAllPaymentsForUser(long userId) {
        List<Payment> payments=paymentRepo.findAllByOrderUserId(userId);
        if(payments.isEmpty())logger.info("No Payments found for user with id {}",userId);
        else logger.info("Found {} Payments for user with id {}",payments.size(),userId);
        return payments;
    }

    @Override
    public Payment getPaymentForOrder(long orderId) {
            return getPaymentByOrderId(orderId);
    }

    @Override
    public boolean isPaymentCompleted(long paymentId) {
        Payment payment=paymentRepo.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment with id "+paymentId+" not found"));
        return PaymentStatus.SUCCESS.equals(payment.getStatus());
    }

    @Override
    public void updatePaymentStatus(long paymentId, PaymentStatus status) {
        Payment payment=paymentRepo.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment with id "+paymentId+" not found"));
        payment.setStatus(status);
        paymentRepo.save(payment);
        logger.info("Payment with id {} updated with status {}",paymentId,status);
    }

    @Override
    public PaymentStatus getPaymentStatus(long paymentId) {
        Payment payment=paymentRepo.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment with id "+paymentId+" not found"));
        return payment.getStatus();
    }
}
