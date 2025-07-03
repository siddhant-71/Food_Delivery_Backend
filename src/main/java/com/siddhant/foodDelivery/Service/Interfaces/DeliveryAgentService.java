package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.DeliveryAgent;
import com.siddhant.foodDelivery.Entities.Order;

import java.util.List;
import java.util.Optional;

public interface DeliveryAgentService {
    //CRUD
    DeliveryAgent registerDeliveryAgent(DeliveryAgent deliveryAgent);
    DeliveryAgent updateDeliveryAgent(long DeliveryAgentID,DeliveryAgent deliveryAgent);
    void deleteDeliveryAgent(DeliveryAgent deliveryAgent);
    List<DeliveryAgent> getAllDeliveryAgents();
    DeliveryAgent getDeliveryAgentById(long id);

    //SEATCH
    List<DeliveryAgent> searchDeliveryAgentsByName(String name);
    List<DeliveryAgent> searchDeliveryAgentsByRatingRange(double start, double end);
    Optional<DeliveryAgent> getDeliveryAgentByPhone(String phone);


    //ASSIGNMENT
    void assignDeliveryAgentToOrder(long deliveryAgentId,long OrderId);
    void unassignDeliveryAgentFromOrder(long deliveryAgentId,long OrderId);
    boolean isDeliveryAgentAssignedToOrder(long deliveryAgentId,long orderId);
    Order getOrderAssignedToDeliveryAgent(long deliveryAgentId);
    List<Order> getTotalOrdersAssignedToDeliveryAgent(long deliveryAgentId);

    //AVAIBILITY

    boolean isDeliveryAgentAvailable(long deliveryAgentId);
    void setDeliveryAgentAvailability(long deliveryAgentId,boolean availability);
    List<DeliveryAgent> getAvailableDeliveryAgents();
    List<DeliveryAgent> getUnavailableDeliveryAgents();


    //UPDATE RATING
    void updateDeliveryAgentRating(long deliveryAgentId,double rating);



    //ADMIN
    long countDeliveryAgents();
    long countAvailableDeliveryAgents();
    long countUnavailableDeliveryAgents();
    void blockDeliveryAgent(long deliveryAgentId);
    void unblockDeliveryAgent(long deliveryAgentId);

    //LCN
    //void updateDeliveryAgentLocation(long deliveryAgentId,double latitude,double longitude);
    //String getDeliveryAgentLocation(long deliveryAgentId);
}
