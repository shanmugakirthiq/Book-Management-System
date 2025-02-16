package com.stradegi.book_management.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanDTO {
    private String planName;
    private int durationDays;
    private double price;
    private double adminCommission;
}
