package com.siddhant.foodDelivery.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String area;
    private String city;
    private String state;
    private double rating;
    private int ratingCount;
    private Integer pincode;
    private String description;
    private String image;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menu;
}
