package com.stradegi.book_management.controller;

import com.stradegi.book_management.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@Slf4j
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @GetMapping("/metrics")
    public ResponseEntity<Object> getDashboardMetrics() {
        log.info("Fetching dashboard metrics");
        var metrics = dashboardService.getDashboardMetrics();
        log.info("Dashboard metrics fetched successfully");
        return ResponseEntity.ok(metrics);
    }
}
