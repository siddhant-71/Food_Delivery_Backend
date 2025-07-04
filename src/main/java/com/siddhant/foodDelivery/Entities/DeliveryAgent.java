package com.siddhant.foodDelivery.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "deliveryAgents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAgent {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private double rating;
    private boolean availability;
    private boolean blocked;
    private String image;
    private long ratingCount;
    private Order currentOrder;
    @OneToMany(mappedBy = "deliveryAgent")
    private List<Order> orders;
}
