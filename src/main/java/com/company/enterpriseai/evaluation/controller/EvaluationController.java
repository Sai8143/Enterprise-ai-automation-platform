package com.company.enterpriseai.evaluation.controller;

import com.company.enterpriseai.evaluation.entity.EvaluationRun;
import com.company.enterpriseai.evaluation.entity.EvaluationTest;
import com.company.enterpriseai.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping("/run-test")
    public ResponseEntity<EvaluationRun> runSingleTest(@RequestBody EvaluationTest test) {
        // Run the AI Evaluation
        EvaluationRun result = evaluationService.runEvaluation(test);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardMetrics() {
        // Mock aggregate metrics for the dashboard
        Map<String, Object> metrics = Map.of(
                "avgRetrievalPrecision", "91.4%",
                "avgRetrievalRecall", "88.7%",
                "avgAnswerAccuracy", "93.2%",
                "hallucinationRate", "3.1%",
                "avgLatencySeconds", 1.7
        );
        return ResponseEntity.ok(metrics);
    }
}
