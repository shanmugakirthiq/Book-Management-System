package com.stradegi.book_management.dao;

import com.stradegi.book_management.model.entity.SubscriptionPlan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionPlanDAO {

    private final JdbcTemplate jdbcTemplate;

    public SubscriptionPlanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addSubscriptionPlan(SubscriptionPlan plan) {
        String sql = "INSERT INTO subscription_plans (plan_name, duration_days, price, admin_commission) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, plan.getPlanName(), plan.getDurationDays(), plan.getPrice(), plan.getAdminCommission());
    }

    public List<SubscriptionPlan> getAllPlans() {
        String sql = "SELECT * FROM subscription_plans";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SubscriptionPlan.class));
    }

    public SubscriptionPlan getPlanById(Long planId) {
        String sql = "SELECT * FROM subscription_plans WHERE subscription_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{planId}, new BeanPropertyRowMapper<>(SubscriptionPlan.class));
    }

    public void updatePlan(Long planId, SubscriptionPlan plan) {
        String sql = "UPDATE subscription_plans SET plan_name = ?, duration_days = ?, price = ?, admin_commission = ? WHERE subscription_id = ?";
        jdbcTemplate.update(sql, plan.getPlanName(), plan.getDurationDays(), plan.getPrice(), plan.getAdminCommission(), planId);
    }

    public void deletePlan(Long planId) {
        String sql = "DELETE FROM subscription_plans WHERE subscription_id = ?";
        jdbcTemplate.update(sql, planId);
    }
}
