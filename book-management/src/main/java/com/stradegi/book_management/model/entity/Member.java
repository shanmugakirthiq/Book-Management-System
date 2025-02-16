package com.stradegi.book_management.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    private String email;
    private String password;
    private String mobileNumber;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    private SubscriptionPlan subscriptionPlan;

    private boolean isActive = false;
    private double walletBalance = 0.0;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;

    private boolean firstLogin = true;
}
