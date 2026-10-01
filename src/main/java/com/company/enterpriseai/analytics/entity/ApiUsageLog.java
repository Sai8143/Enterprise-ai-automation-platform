package com.company.enterpriseai.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_usage_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String userId;

    private String endpoint;
    private String actionType; // e.g., "RAG_QUERY", "AGENT_WORKFLOW"

    private int inputTokens;
    private int outputTokens;
    
    private long latencyMs;
    private boolean successful;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
