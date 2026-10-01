package com.company.enterpriseai.workflow.repository;

import com.company.enterpriseai.workflow.entity.WorkflowJob;
import com.company.enterpriseai.workflow.enums.WorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowJobRepository extends JpaRepository<WorkflowJob, String> {
    List<WorkflowJob> findByTenantIdAndStatus(String tenantId, WorkflowStatus status);
}
