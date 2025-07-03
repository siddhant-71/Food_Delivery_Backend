package com.siddhant.foodDelivery.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "Restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String location;
    private String city;
    private String state;
    private double rating;
    private String image;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menu;
}
