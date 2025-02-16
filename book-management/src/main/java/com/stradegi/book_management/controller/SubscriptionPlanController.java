package com.stradegi.book_management.controller;

import com.stradegi.book_management.model.entity.SubscriptionPlan;
import com.stradegi.book_management.model.dto.SubscriptionPlanDTO;
import com.stradegi.book_management.service.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/subscription-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionPlanController.class);

    @PostMapping("/add")
    public String addSubscriptionPlan(@RequestBody SubscriptionPlanDTO planDTO) {
        logger.info("Adding new subscription plan: {}", planDTO.getPlanName());
        return subscriptionPlanService.addPlan(planDTO);
    }

    @GetMapping("/all")
    public List<SubscriptionPlan> getAllPlans() {
        logger.info("Fetching all subscription plans.");
        return subscriptionPlanService.getAllPlans();
    }

    @GetMapping("/{planId}")
    public SubscriptionPlan getPlanById(@PathVariable Long planId) {
        logger.info("Fetching subscription plan with ID: {}", planId);
        return subscriptionPlanService.getPlanById(planId);
    }

    @PutMapping("/update/{planId}")
    public String updateSubscriptionPlan(@PathVariable Long planId, @RequestBody SubscriptionPlanDTO planDTO) {
        logger.info("Updating subscription plan with ID: {}", planId);
        subscriptionPlanService.updatePlan(planId, planDTO);
        return "Subscription plan updated successfully!";
    }

    @DeleteMapping("/delete/{planId}")
    public String deleteSubscriptionPlan(@PathVariable Long planId) {
        logger.info("Attempting to delete subscription plan with ID: {}", planId);
        subscriptionPlanService.deletePlan(planId);
        return "Subscription plan deleted successfully.";
    }
}
