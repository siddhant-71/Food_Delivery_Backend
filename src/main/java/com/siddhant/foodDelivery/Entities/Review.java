package com.siddhant.foodDelivery.Entities;

import com.siddhant.foodDelivery.Enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue
    private long id;
    private String comment;
    private Integer rating;
    private LocalDateTime reviewDate;
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

}
