package com.rschir.prac.services.llm;

public interface LLMService {
    String sendMessage(String prompt);

    String modifyPrompt(String prompt);
}
