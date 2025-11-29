package com.rschir.prac.controllers;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.services.llm.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("llm")
public class LLMController {

    LLMService llmService;

    @Autowired
    public LLMController(LLMService llmService){
        this.llmService = llmService;
    }

    @PostMapping(value = "/ask", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> postJewelry(@RequestParam String text) {

        System.out.println(text);

        return new ResponseEntity<>(llmService.sendMessage(text), HttpStatus.OK);
    }
}
