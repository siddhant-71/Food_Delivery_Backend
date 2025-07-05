package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.DTOs.OrderRequest;
import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {

    Order placeOrder(long userId, OrderRequest orderRequest);
    Order getOrderById(long orderId);
    List<Order> getOrderOfUsers(long userId);
    List<Order>getAllOrders();



    void updateOrderStatus(long OrderId, OrderStatus status);
    String getOrderStatus(long OrderId);

    void assignDeliveryAgent(long orderId,long AgentId);
    void unassignDeliveryAgent(long OrderId,long  AgentId);

    List<Order> getOrderByStatus(OrderStatus status);
    List<Order> getOrderByDateRange(LocalDateTime start, LocalDateTime end);

    double calculateOrderTotal(long orderId);
    long countTotalOrders();
    long countOrdersByStatus(OrderStatus status);
}
