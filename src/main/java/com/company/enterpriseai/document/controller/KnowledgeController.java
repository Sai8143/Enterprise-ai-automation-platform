package com.company.enterpriseai.document.controller;

import com.company.enterpriseai.document.DocumentIngestionService;
import com.company.enterpriseai.rag.EnterpriseRagAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final DocumentIngestionService ingestionService;
    private final EnterpriseRagAgent ragAgent;

    @PostMapping("/ingest")
    public ResponseEntity<String> ingestDocument(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestParam("file") MultipartFile file) {
        
        try {
            ingestionService.ingestPdf(file.getInputStream(), tenantId, file.getOriginalFilename());
            return ResponseEntity.ok("Document ingested, chunked, and embedded successfully.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to read file.");
        }
    }

    @PostMapping("/query")
    public ResponseEntity<String> queryKnowledgeBase(
            @RequestHeader("X-Tenant-ID") String tenantId, // Would be used for metadata filtering in a real Vector DB
            @RequestBody String query) {
        
        String answer = ragAgent.answerKnowledgeQuestion(query);
        return ResponseEntity.ok(answer);
    }
}
