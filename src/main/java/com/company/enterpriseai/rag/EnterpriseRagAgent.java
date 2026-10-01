package com.company.enterpriseai.rag;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface EnterpriseRagAgent {

    @SystemMessage("""
            You are an Enterprise Knowledge Assistant.
            Answer the user's questions based ONLY on the provided retrieved context.
            If the answer is not in the context, say "I cannot find the answer in the enterprise knowledge base."
            Do not hallucinate. Provide citations to the source documents if possible.
            """)
    String answerKnowledgeQuestion(@UserMessage String userQuestion);
}
