package com.company.enterpriseai.evaluation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluation_tests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String expectedAnswer;
    
    @Column(nullable = false)
    private String expectedDocumentName; // The document that should contain the answer
}
