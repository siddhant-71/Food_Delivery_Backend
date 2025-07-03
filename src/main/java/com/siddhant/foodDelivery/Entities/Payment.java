package com.siddhant.foodDelivery.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue
    private long id;
    private String paymentMode;
    private String status;
    private String transactionId;
    private double amount;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
