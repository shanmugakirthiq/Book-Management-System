package com.stradegi.book_management.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;
    private String planName;
    private int durationDays;
    private double price;
    private double adminCommission;
}
