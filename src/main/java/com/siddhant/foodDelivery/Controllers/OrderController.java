package com.siddhant.foodDelivery.Controllers;


import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Enums.OrderStatus;
import com.siddhant.foodDelivery.Service.Implementations.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/")
    List<Order> getAll(){
        return orderServiceImpl.getAllOrders();
    }
    @GetMapping("/{id}")
    Order getById(@PathVariable("id") long id){
        return orderServiceImpl.getOrderById(id);
    }
    @GetMapping("/user/{id}")
    List<Order> getByUser(@PathVariable("id")long id){
        return orderServiceImpl.getOrderOfUsers(id);
    }
    @GetMapping("/status/{status}")
    List<Order> getByStatus(@PathVariable("status") OrderStatus status){
        return orderServiceImpl.getOrderByStatus(status);
    }
    @GetMapping("/data/{date}")
    List<Order> getByDateRange(@PathVariable("data")LocalDateTime date){
        return orderServiceImpl.getOrderByDateRange(date,LocalDateTime.now());
    }
    @PostMapping("/add")
    void add(@RequestBody Order order){
        orderServiceImpl.savet(order);
    }
}
