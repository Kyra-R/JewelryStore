package com.rschir.prac.config;

import com.rschir.prac.services.llm.LLMService;
import com.rschir.prac.services.llm.OllamaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    @Bean
    public LLMService llmService(
            @Value("${llm.provider}") String provider,
            OllamaService ollama
            //OpenAIService openai
    ) {
        return switch (provider) {
            case "ollama" -> ollama;
            //case "openai" -> openai;
            default -> throw new IllegalArgumentException("Unknown LLM provider");
        };
    }
}
