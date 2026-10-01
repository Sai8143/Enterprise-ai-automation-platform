package com.company.enterpriseai.agent.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseTools {

    @Tool("Finds invoices that are overdue by a specific number of days")
    public String findOverdueInvoices(int daysOverdue) {
        log.info("Agent called tool: findOverdueInvoices with daysOverdue={}", daysOverdue);
        // Mocking a database call for demonstration
        if (daysOverdue == 30) {
            return "[{\"invoiceId\": \"INV-1029\", \"customerId\": \"CUST-88\", \"amount\": 4500.00, \"daysOverdue\": 32}," +
                   "{\"invoiceId\": \"INV-1033\", \"customerId\": \"CUST-12\", \"amount\": 12000.00, \"daysOverdue\": 35}]";
        }
        return "[]";
    }
}
