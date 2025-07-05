package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.DTOs.OrderRequest;
import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Enums.OrderStatus;
import com.siddhant.foodDelivery.Enums.PaymentMode;
import com.siddhant.foodDelivery.Exceptions.AddressExceptions.AddressNotFoundException;
import com.siddhant.foodDelivery.Exceptions.OrderExceptions.OrderNotFoundException;
import com.siddhant.foodDelivery.Exceptions.RestaurantExceptions.RestaurantNotFoundException;
import com.siddhant.foodDelivery.Exceptions.UserException.UserNotFoundException;
import com.siddhant.foodDelivery.Repository.AddressRepo;
import com.siddhant.foodDelivery.Repository.OrderRepo;
import com.siddhant.foodDelivery.Repository.RestaurantRepo;
import com.siddhant.foodDelivery.Repository.UserRepo;
import com.siddhant.foodDelivery.Service.Interfaces.DeliveryAgentService;
import com.siddhant.foodDelivery.Service.Interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    final static Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    DeliveryAgentService deliveryAgentService;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Override
    @Transactional
    public Order placeOrder(long userId, OrderRequest orderRequest) {
        if(orderRequest==null)throw new NullPointerException("Order Request is null");
        Order order=new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setPaymentMethod(orderRequest.getPaymentMode());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setRestaurant(restaurantRepo.findById(orderRequest.getRestaurantId()).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+orderRequest.getRestaurantId()+" not found")));
        order.setUser(userRepo.findById(orderRequest.getUserId()).orElseThrow(()->new UserNotFoundException("User with id "+orderRequest.getUserId()+" not found")));
        order.setDeliveryAddress(addressRepo.findById(orderRequest.getAddressId()).orElseThrow(()->new AddressNotFoundException("Address with id "+orderRequest.getAddressId()+" not found")));
        orderRepo.save(order);
        logger.info("Order placed for user with id {} and Name -{} at {}",userId,order.getUser().getName(),order.getOrderTime());
        return order;
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
    }

    @Override
    public List<Order> getOrderOfUsers(long userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User with the id "+userId+" not found"));
        List<Order> ans=orderRepo.findAllByUserId(userId);
        if(ans.isEmpty())logger.info("No Orders found for user with id {} ",userId);
        else logger.info("Found {} Orders for user with id {}",ans.size(),userId);
        return ans;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    @Transactional
    public void updateOrderStatus(long orderId, OrderStatus status) {
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
        order.setStatus(status);
        orderRepo.save(order);
        logger.info("Order with id {} updated with status {}",orderId,status);
    }

    @Override
    public String getOrderStatus(long orderId) {
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
        return order.getStatus().toString();
    }

    @Override
    @Transactional
    public void assignDeliveryAgent(long orderId, long agentId) {
        deliveryAgentService.assignDeliveryAgentToOrder(orderId,agentId);
    }

    @Override
    @Transactional
    public void unassignDeliveryAgent(long orderId, long agentId) {
        deliveryAgentService.unassignDeliveryAgentFromOrder(orderId,agentId);
    }

    @Override
    public List<Order> getOrderByStatus(OrderStatus status) {
        if(orderRepo.findAllByStatus(status).isEmpty())logger.info("No Orders found with status {}",status);
        else logger.info("Found {} Orders with status {}",orderRepo.findAllByStatus(status).size(),status);
        return orderRepo.findAllByStatus(status);
    }

    @Override
    public List<Order> getOrderByDateRange(LocalDateTime start, LocalDateTime end) {
        if(orderRepo.findAllByOrderTimeBetween(start,end).isEmpty())logger.info("No Orders found between {} and {}",start,end);
        else logger.info("Found {} Orders between {} and {}",orderRepo.findAllByOrderTimeBetween(start,end).size(),start,end);
        return orderRepo.findAllByOrderTimeBetween(start,end);
    }

    @Override
    public double calculateOrderTotal(long orderId) {
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
        double total=0.00;
        List<Dish>dishes=order.getDishes();
        if(dishes==null || dishes.isEmpty()){
            logger.warn("No dishes found in order with id {}",orderId);
            return 0.00;
        }
        for(Dish dish:dishes){
            total+=dish.getPrice();
        }
        logger.info("Total Order Amount is {}",total);
        return total;
    }

    @Override
    public long countTotalOrders() {
        logger.info("Total Orders are {}",orderRepo.count());
        return orderRepo.count();
    }

    @Override
    public long countOrdersByStatus(OrderStatus status) {
        logger.info("Total Orders with status {} are {}",status,orderRepo.findAllByStatus(status).size());
        return orderRepo.findAllByStatus(status).size();
    }
}
