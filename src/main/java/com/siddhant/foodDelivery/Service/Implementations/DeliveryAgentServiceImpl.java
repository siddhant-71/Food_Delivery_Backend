package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.DeliveryAgent;
import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Exceptions.DeliveryAgentExceptions.DeliveryAgentNotAssignedException;
import com.siddhant.foodDelivery.Exceptions.DeliveryAgentExceptions.DeliveryAgentNotFoundException;
import com.siddhant.foodDelivery.Exceptions.OrderExceptions.OrderNotFoundException;
import com.siddhant.foodDelivery.Repository.DeliveryAgentRepo;
import com.siddhant.foodDelivery.Repository.OrderRepo;
import com.siddhant.foodDelivery.Service.Interfaces.DeliveryAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryAgentServiceImpl implements DeliveryAgentService {

    private static final Logger logger= LoggerFactory.getLogger(DeliveryAgentServiceImpl.class);

    @Autowired
    private DeliveryAgentRepo deliveryAgentRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Override
    public DeliveryAgent registerDeliveryAgent(DeliveryAgent deliveryAgent) {
        deliveryAgentRepo.save(deliveryAgent);
        return deliveryAgent;
    }

    @Override
    public DeliveryAgent updateDeliveryAgent(long deliveryAgentID, DeliveryAgent deliveryAgent) {
        DeliveryAgent oldDeliveryAgent=deliveryAgentRepo.findById(deliveryAgentID).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentID+" not found"));
        if(deliveryAgent.getName()!=null)oldDeliveryAgent.setName(deliveryAgent.getName());
        if(deliveryAgent.getPhone()!=null)oldDeliveryAgent.setPhone(deliveryAgent.getPhone());
        if(deliveryAgent.getEmail()!=null)oldDeliveryAgent.setEmail(deliveryAgent.getEmail());
        if(deliveryAgent.getImage()!=null)oldDeliveryAgent.setImage(deliveryAgent.getImage());
        deliveryAgentRepo.save(oldDeliveryAgent);
        logger.info("Delivery Agent with id {} updated successfully",deliveryAgentID);
        return oldDeliveryAgent;
    }

    @Override
    public void deleteDeliveryAgent(DeliveryAgent deliveryAgent) {
        deliveryAgentRepo.delete(deliveryAgent);
        logger.info("Delivery Agent with id {} deleted successfully",deliveryAgent.getId());
    }

    @Override
    public List<DeliveryAgent> getAllDeliveryAgents() {
        if(deliveryAgentRepo.count()==0)logger.info("No Delivery Agents found");
        return deliveryAgentRepo.findAll();
    }

    @Override
    public DeliveryAgent getDeliveryAgentById(long id) {
        return deliveryAgentRepo.findById(id).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+id+" not found"));
    }

    @Override
    public List<DeliveryAgent> searchDeliveryAgentsByName(String name) {
        if(deliveryAgentRepo.findAllByName(name).isEmpty())logger.info("No Delivery Agents found with name {}",name);
        return deliveryAgentRepo.findAllByName(name);
    }

    @Override
    public List<DeliveryAgent> searchDeliveryAgentsByRatingRange(double start, double end) {
        if(deliveryAgentRepo.findAllByRatingBetween(start,end).isEmpty())logger.info("No Delivery Agents found with rating between {} and {}",start,end);
        return deliveryAgentRepo.findAllByRatingBetween(start,end);
    }

    @Override
    public DeliveryAgent getDeliveryAgentByPhone(String phone) {
        return deliveryAgentRepo.findByPhone(phone).orElseThrow(()->new DeliveryAgentNotFoundException("delivery agent with phone "+phone+" not found"));
    }

    @Override
    @Transactional
    public void assignDeliveryAgentToOrder(long orderId,long deliveryAgentId) {
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        if(!deliveryAgent.isBlocked() && deliveryAgent.getCurrentOrder()==null){
            order.setDeliveryAgent(deliveryAgent);
            deliveryAgent.setCurrentOrder(order);
            deliveryAgent.setAvailability(false);
            orderRepo.save(order);
            deliveryAgentRepo.save(deliveryAgent);
            logger.info("Delivery Agent with id {} assigned to Order with Id {}",deliveryAgentId,orderId);
        }
        else{
            if(deliveryAgent.isBlocked())logger.warn("Delivery Agent with id {} is blocked",deliveryAgentId);
            else logger.warn("Delivery Agent with id {} is not available",deliveryAgentId);
            throw new DeliveryAgentNotAssignedException("Delivery Agent with id "+deliveryAgentId+" is not available to be Assigned to Order with Id "+orderId);
        }
    }

    @Override
    @Transactional
    public void unassignDeliveryAgentFromOrder(long orderId,long deliveryAgentId) {
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found"));
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        if(order.getDeliveryAgent()!=null && deliveryAgent.getCurrentOrder()!=null && order.getDeliveryAgent().equals(deliveryAgent) && deliveryAgent.getCurrentOrder().equals(order)){
            deliveryAgent.setCurrentOrder(null);
            order.setDeliveryAgent(null);
            deliveryAgent.setAvailability(true);
            deliveryAgentRepo.save(deliveryAgent);
            Order savedOrder=orderRepo.save(order);
            return ;
        }
        else{
            logger.warn("Delivery Agent with id {} was not already assigned to Order with Id {}",deliveryAgentId,orderId);
        }
    }

    @Override
    public boolean isDeliveryAgentAssignedToOrder(long deliveryAgentId, long orderId) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        return deliveryAgent.getCurrentOrder() != null && deliveryAgent.isAvailability();
    }

    @Override
    public Order getOrderAssignedToDeliveryAgent(long deliveryAgentId) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        if(deliveryAgent.getCurrentOrder()==null)logger.info("Delivery Agent with id {} is not assigned to any order",deliveryAgentId);
        return deliveryAgent.getCurrentOrder();
    }

    @Override
    public List<Order> getTotalOrdersAssignedToDeliveryAgent(long deliveryAgentId) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        return deliveryAgent.getOrders();
    }

    @Override
    public boolean isDeliveryAgentAvailable(long deliveryAgentId) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        return deliveryAgent.getCurrentOrder() == null;
    }


    @Override
    public List<DeliveryAgent> getAvailableDeliveryAgents() {
        List<DeliveryAgent> deliveryAgents=deliveryAgentRepo.findAll();
        return deliveryAgents.stream().filter(deliveryAgent -> deliveryAgent.getCurrentOrder()==null).toList();
    }

    @Override
    public List<DeliveryAgent> getUnavailableDeliveryAgents() {
        List<DeliveryAgent> deliveryAgents=deliveryAgentRepo.findAll();
        return deliveryAgents.stream().filter(deliveryAgent -> deliveryAgent.getCurrentOrder()!=null).toList();
    }

    @Override
    public void updateDeliveryAgentRating(long deliveryAgentId, double rating) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        double oldRating=deliveryAgent.getRating() * deliveryAgent.getRatingCount();
        oldRating+=rating;
        deliveryAgent.setRatingCount(deliveryAgent.getRatingCount()+1);
        deliveryAgent.setRating(oldRating/deliveryAgent.getRatingCount());
        logger.info(" Ratings of Delivery Agent {} updated successfully",deliveryAgent.getName());
    }

    @Override
    public long countDeliveryAgents() {
        return deliveryAgentRepo.count();
    }

    @Override
    public long countAvailableDeliveryAgents() {
        return deliveryAgentRepo.countAllByAvailability(true);
    }

    @Override
    public long countUnavailableDeliveryAgents() {
        return deliveryAgentRepo.countAllByAvailability(false);
    }

    @Override
    public void blockDeliveryAgent(long deliveryAgentId) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        deliveryAgent.setBlocked(true);
        deliveryAgentRepo.save(deliveryAgent);
        logger.info("Delivery Agent with id {} blocked successfully",deliveryAgentId);
    }

    @Override
    public void unblockDeliveryAgent(long deliveryAgentId) {
        DeliveryAgent deliveryAgent=deliveryAgentRepo.findById(deliveryAgentId).orElseThrow(()->new DeliveryAgentNotFoundException("Delivery Agent with id "+deliveryAgentId+" not found"));
        deliveryAgent.setBlocked(false);
        deliveryAgentRepo.save(deliveryAgent);
        logger.info("Delivery Agent with id {} unblocked successfully",deliveryAgentId);
    }
}

//long countAllByAvailability(boolean availability); inserted this inside repo