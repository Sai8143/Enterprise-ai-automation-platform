package com.company.enterpriseai.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface WorkflowAgent {

    @SystemMessage("""
            You are an Enterprise AI Agent responsible for analyzing business requests.
            Your task is to understand the user's request, gather necessary data using your tools,
            and propose a concrete action payload (JSON format) and a recommendation.
            
            Always output your final answer as a JSON object with two fields:
            - recommendation: A human-readable explanation of what you found and what should be done.
            - proposedActionPayload: A JSON string containing the technical payload required to execute the action.
            """)
    String analyzeAndProposeAction(@UserMessage String userRequest);
}
