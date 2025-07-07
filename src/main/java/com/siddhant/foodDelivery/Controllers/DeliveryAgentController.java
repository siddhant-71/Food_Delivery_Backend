package com.siddhant.foodDelivery.Controllers;


import com.siddhant.foodDelivery.Entities.DeliveryAgent;
import com.siddhant.foodDelivery.Service.Implementations.DeliveryAgentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent/")
public class DeliveryAgentController {
    @Autowired
    DeliveryAgentServiceImpl agent;

    @GetMapping("")
    List<DeliveryAgent> getAll(){
        return agent.getAllDeliveryAgents();
    }
    @GetMapping("{id}")
    DeliveryAgent getById(@PathVariable("id")long id){
        return agent.getDeliveryAgentById(id);
    }
    @GetMapping("rating/{rating}")
    List<DeliveryAgent> getByRating(@PathVariable("rating")double rating){
        return agent.searchDeliveryAgentsByRatingRange(rating,5.00);
    }
    @GetMapping("phone/{number}")
    DeliveryAgent getNByPhone(@PathVariable("number")String number){
        return agent.getDeliveryAgentByPhone(number);
    }
    @GetMapping("name/{name}")
    List<DeliveryAgent> getByName(@PathVariable("name")String name){
        return agent.searchDeliveryAgentsByName(name);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable("id")long id){
        DeliveryAgent a=agent.getDeliveryAgentById(id);
        agent.deleteDeliveryAgent(a);
    }
    @PostMapping("")
    void add(@RequestBody DeliveryAgent deliveryAgent){
        agent.registerDeliveryAgent(deliveryAgent);
    }
    @PutMapping("{id}")
    void update(@PathVariable("id")long id,@RequestBody DeliveryAgent deliveryAgent){
        agent.updateDeliveryAgent(id,deliveryAgent);
    }
}
