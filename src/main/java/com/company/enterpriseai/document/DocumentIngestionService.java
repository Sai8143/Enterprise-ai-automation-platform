package com.company.enterpriseai.document;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentIngestionService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<dev.langchain4j.data.segment.TextSegment> embeddingStore;

    public void ingestPdf(InputStream pdfStream, String tenantId, String documentName) {
        log.info("Starting ingestion for document: {} (Tenant: {})", documentName, tenantId);
        
        DocumentParser parser = new ApachePdfBoxDocumentParser();
        Document document = parser.parse(pdfStream);
        
        // Add metadata for tenant isolation and search filtering
        document.metadata().add("tenantId", tenantId);
        document.metadata().add("documentName", documentName);

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 50)) // Chunking strategy
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(document);
        log.info("Successfully chunked, embedded, and stored document: {}", documentName);
    }
}
