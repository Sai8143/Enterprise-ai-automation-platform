package com.company.enterpriseai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EnterpriseAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseAiApplication.class, args);
    }
}
