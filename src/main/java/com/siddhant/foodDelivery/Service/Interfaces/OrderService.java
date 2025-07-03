package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.DTOs.OrderRequest;
import com.siddhant.foodDelivery.Entities.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {

    Order placeOrder(long userId, OrderRequest orderRequest);
    Order getOrderById(long orderId);
    List<Order> getOrderOfUsers(long userId);
    List<Order>getAllOrders();



    void updateOrderStatus(long OrderId,String status);
    String getOrderStatus(long OrderId);

    void assignDeliveryAgent(long orderId,long AgentId);
    void unassignDeliveryAgent(long OrderId,long  AgentId);

    List<Order> getOrderByStatus(String status);
    List<Order> getOrderByDateRange(LocalDate start, LocalDate end);

    double calculateOrderTotal(long orderId);
    long countTotalOrders();
    long countOrdersByStatus(String status);
}
