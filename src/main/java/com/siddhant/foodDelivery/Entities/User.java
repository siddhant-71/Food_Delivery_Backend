package com.siddhant.foodDelivery.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String role;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Cart cart;
}
