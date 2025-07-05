package com.siddhant.foodDelivery.Entities;

import com.siddhant.foodDelivery.Enums.PaymentMode;
import com.siddhant.foodDelivery.Enums.PaymentStatus;
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
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String transactionId;
    private double amount;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
