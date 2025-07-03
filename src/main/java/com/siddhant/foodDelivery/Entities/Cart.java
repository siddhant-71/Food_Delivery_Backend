package com.siddhant.foodDelivery.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "cart_dishes",
                joinColumns = @JoinColumn(name = "cart_id"),
                inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;
}
