package com.company.enterpriseai.workflow.entity;

import com.company.enterpriseai.workflow.enums.WorkflowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "requested_by", nullable = false)
    private String requestedBy;

    @Column(columnDefinition = "TEXT")
    private String originalQuery;

    @Column(columnDefinition = "TEXT")
    private String aiRecommendation;

    @Column(columnDefinition = "TEXT")
    private String proposedActionPayload;

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
