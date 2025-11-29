package com.rschir.prac.services.llm;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class OllamaService implements LLMService {
    private RestTemplate rest;

    public OllamaService()
    {

        rest = new RestTemplate();
        // это для кириллицы
        System.out.println(rest.getMessageConverters().removeIf(c -> c instanceof StringHttpMessageConverter));
        rest.getMessageConverters().add(0,
                new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public String modifyPrompt(String prompt) {
        String newPrompt;
        if (prompt.matches(".*\\p{IsCyrillic}.*")) {
            newPrompt = "Ты - продавец в ювелирном магазине. Твоя цель - помогать с выбором товара. " +
                    "Клиент спрашивает следующее: " + prompt;
        } else {
            newPrompt = "You're consultant in a jewelry store. Your goal is to help the customers decide what to buy. " +
                    "The customer tells you: " + prompt;
        }
        return newPrompt;
    }

    @Override
    public String sendMessage(String prompt) {
        Map<String, Object> request = Map.of(
                "model", "llama3.1",
                "prompt", modifyPrompt(prompt),
                "stream", false
        );


        Map response = rest.postForObject(
                "http://localhost:11434/api/generate",
                request,
                Map.class
        );


        Object text = response.get("response");

        System.out.println(text);

        return text.toString();
    }
}
