package com.company.enterpriseai.analytics.controller;

import com.company.enterpriseai.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class DashboardController {

    private final AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getControlCenterMetrics(
            @RequestHeader("X-Tenant-ID") String tenantId) {
        
        // This endpoint is secured by JwtAuthFilter and requires ROLE_MANAGER or ROLE_ADMIN
        Map<String, Object> metrics = analyticsService.getEnterpriseControlCenterMetrics(tenantId);
        return ResponseEntity.ok(metrics);
    }
}
