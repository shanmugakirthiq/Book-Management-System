package com.stradegi.book_management.service;

import com.stradegi.book_management.model.entity.SubscriptionPlan;
import com.stradegi.book_management.model.dto.SubscriptionPlanDTO;

import java.util.List;

public interface SubscriptionPlanService {
    String addPlan(SubscriptionPlanDTO planDTO);
    List<SubscriptionPlan> getAllPlans();
    SubscriptionPlan getPlanById(Long planId);
    void updatePlan(Long planId, SubscriptionPlanDTO planDTO);
    void deletePlan(Long planId);
}
