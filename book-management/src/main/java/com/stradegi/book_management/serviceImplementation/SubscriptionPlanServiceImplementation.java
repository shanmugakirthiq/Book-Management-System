package com.stradegi.book_management.serviceImplementation;

import com.stradegi.book_management.dao.SubscriptionPlanDAO;
import com.stradegi.book_management.model.entity.SubscriptionPlan;
import com.stradegi.book_management.model.dto.SubscriptionPlanDTO;
import com.stradegi.book_management.service.SubscriptionPlanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionPlanServiceImplementation implements SubscriptionPlanService {

    private final SubscriptionPlanDAO subscriptionPlanDAO;
    private final ModelMapper modelMapper;

    public SubscriptionPlanServiceImplementation(SubscriptionPlanDAO subscriptionPlanDAO, ModelMapper modelMapper) {
        this.subscriptionPlanDAO = subscriptionPlanDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addPlan(SubscriptionPlanDTO planDTO) {
        SubscriptionPlan plan = modelMapper.map(planDTO, SubscriptionPlan.class);
        subscriptionPlanDAO.addSubscriptionPlan(plan);
        return "Subscription plan added successfully!";
    }

    @Override
    public List<SubscriptionPlan> getAllPlans() {
        return subscriptionPlanDAO.getAllPlans();
    }

    @Override
    public SubscriptionPlan getPlanById(Long planId) {
        return subscriptionPlanDAO.getPlanById(planId);
    }

    @Override
    public void updatePlan(Long planId, SubscriptionPlanDTO planDTO) {
        SubscriptionPlan plan = modelMapper.map(planDTO, SubscriptionPlan.class);
        subscriptionPlanDAO.updatePlan(planId, plan);
    }

    @Override
    public void deletePlan(Long planId) {
        subscriptionPlanDAO.deletePlan(planId);
    }
}
