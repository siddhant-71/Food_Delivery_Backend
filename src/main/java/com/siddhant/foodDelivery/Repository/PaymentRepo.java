package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Payment;
import com.siddhant.foodDelivery.Enums.OrderStatus;
import com.siddhant.foodDelivery.Enums.PaymentMode;
import com.siddhant.foodDelivery.Enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByTransactionId(String transactionId);
    boolean existsByOrderId(long orderId);
    List<Payment> findAllByStatus(PaymentStatus status);
    Optional<Payment> findByOrderId(long orderId);
    List<Payment> findAllByAmountBetween(double start, double end);
    List<Payment> findAllByAmount(double amount);
    List<Payment> findAllByPaymentMode(PaymentMode paymentMode);
    List<Payment> findAllByOrderOrderTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findAllByOrderDeliveryTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findAllByOrderStatus(OrderStatus status);
    Optional<Payment> findByOrderRestaurantName(String name);
    List<Payment> findAllByOrderUserId(long userId);
}
