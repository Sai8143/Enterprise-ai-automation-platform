package com.company.enterpriseai.evaluation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation_runs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRun {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private EvaluationTest test;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    private double retrievalPrecision; // % of retrieved chunks that were relevant
    private double retrievalRecall;    // % of expected chunks that were retrieved
    private double answerAccuracy;     // Semantic similarity to expected answer
    private boolean hallucinationDetected;
    
    private long latencyMs;

    @CreationTimestamp
    private LocalDateTime runDate;
}
