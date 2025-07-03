package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(long userId);
    List<Order> findAllByUserName(String name);
    List<Order> findAllByUserPhone(String phone);
    List<Order> findAllByDeliveryAddressArea(String area);
    List<Order> findAllByDeliveryAddressCity(String city);
    List<Order> findAllByDeliveryAddressState(String state);
    List<Order> findAllByDeliveryAddressPincode(String pincode);
    List<Order> findAllByOrderTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findAllByDeliveryTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findAllByStatus(String status);
    List<Order> findAllByPaymentMode(String paymentMode);
    List<Order> findAllByPaymentModeAndUserId(String paymentMode,long userId);
    List<Order> findAllByTotalAmountBetween(double start, double end);
    List<Order> findAllByRestaurantName(String name);
    List<Order> findAllByRestaurantId(long restaurantId);
    List<Order> findAllByDeliveryAgentName(String name);
    List<Order> findAllByDeliveryAgentId(long agentId);
    List<Order> findAllByDeliveryAgentPhone(String phone);
    List<Order> findAllByDeliveryAgentEmail(String email);

}
