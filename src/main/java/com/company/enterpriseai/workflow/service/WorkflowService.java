package com.company.enterpriseai.workflow.service;

import com.company.enterpriseai.agent.WorkflowAgent;
import com.company.enterpriseai.workflow.entity.WorkflowJob;
import com.company.enterpriseai.workflow.enums.WorkflowStatus;
import com.company.enterpriseai.workflow.repository.WorkflowJobRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowService {

    private final WorkflowJobRepository jobRepository;
    private final WorkflowAgent workflowAgent;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public WorkflowJob submitQuery(String tenantId, String requestedBy, String query) {
        WorkflowJob job = WorkflowJob.builder()
                .tenantId(tenantId)
                .requestedBy(requestedBy)
                .originalQuery(query)
                .status(WorkflowStatus.ANALYZING)
                .build();
        return jobRepository.save(job);
    }

    @Async
    @Transactional
    public void processJob(String jobId) {
        WorkflowJob job = jobRepository.findById(jobId).orElseThrow();
        try {
            log.info("Agent is analyzing job {}", jobId);
            String agentResponse = workflowAgent.analyzeAndProposeAction(job.getOriginalQuery());
            
            JsonNode responseJson = objectMapper.readTree(agentResponse);
            
            job.setAiRecommendation(responseJson.path("recommendation").asText());
            job.setProposedActionPayload(responseJson.path("proposedActionPayload").toString());
            job.setStatus(WorkflowStatus.PENDING_APPROVAL);
            
            jobRepository.save(job);
            log.info("Job {} requires human approval", jobId);
            // In a real system, send a WebSocket or Push Notification here.
        } catch (Exception e) {
            log.error("Failed to process job {}", jobId, e);
            job.setStatus(WorkflowStatus.FAILED);
            jobRepository.save(job);
        }
    }
}
