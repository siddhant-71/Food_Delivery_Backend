package com.siddhant.foodDelivery.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dishes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dish {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int price;
    private String description;
    private double rating;
    private String image;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
