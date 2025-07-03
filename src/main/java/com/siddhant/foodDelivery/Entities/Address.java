package com.siddhant.foodDelivery.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue
    private long id;
    private String houseNo;
    private String area;
    private String street;
    private String city;
    private int pincode;
    private String landmark;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
