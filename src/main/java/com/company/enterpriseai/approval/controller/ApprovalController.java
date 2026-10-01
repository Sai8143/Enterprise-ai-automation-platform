package com.company.enterpriseai.approval.controller;

import com.company.enterpriseai.workflow.entity.WorkflowJob;
import com.company.enterpriseai.workflow.enums.WorkflowStatus;
import com.company.enterpriseai.workflow.repository.WorkflowJobRepository;
import com.company.enterpriseai.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workflows")
@RequiredArgsConstructor
public class ApprovalController {

    private final WorkflowService workflowService;
    private final WorkflowJobRepository jobRepository;

    @PostMapping("/submit")
    public ResponseEntity<WorkflowJob> submitWorkflow(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestHeader("X-User-ID") String userId,
            @RequestBody String query) {
        
        WorkflowJob job = workflowService.submitQuery(tenantId, userId, query);
        workflowService.processJob(job.getId()); // Async processing
        return ResponseEntity.ok(job);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<WorkflowJob>> getPendingApprovals(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(jobRepository.findByTenantIdAndStatus(tenantId, WorkflowStatus.PENDING_APPROVAL));
    }

    @PostMapping("/{jobId}/approve")
    public ResponseEntity<WorkflowJob> approveJob(@PathVariable String jobId) {
        WorkflowJob job = jobRepository.findById(jobId).orElseThrow();
        if (job.getStatus() != WorkflowStatus.PENDING_APPROVAL) {
            return ResponseEntity.badRequest().build();
        }
        
        job.setStatus(WorkflowStatus.APPROVED);
        // Here we would trigger the actual execution queue
        jobRepository.save(job);
        
        return ResponseEntity.ok(job);
    }

    @PostMapping("/{jobId}/reject")
    public ResponseEntity<WorkflowJob> rejectJob(@PathVariable String jobId) {
        WorkflowJob job = jobRepository.findById(jobId).orElseThrow();
        if (job.getStatus() != WorkflowStatus.PENDING_APPROVAL) {
            return ResponseEntity.badRequest().build();
        }
        
        job.setStatus(WorkflowStatus.REJECTED);
        jobRepository.save(job);
        
        return ResponseEntity.ok(job);
    }
}
