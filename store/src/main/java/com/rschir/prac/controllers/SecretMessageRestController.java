package com.rschir.prac.controllers;

import com.rschir.prac.model.SecretMessage;
import com.rschir.prac.services.SecretMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/secret")
//@CrossOrigin("*")
public class SecretMessageRestController {

    SecretMessageService secretMessageService;

    @Autowired
    public SecretMessageRestController(SecretMessageService secretMessageService) {
        this.secretMessageService = secretMessageService;

    }


    @GetMapping
    public SecretMessage getSecretMessageById() {

        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails()); //id
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return secretMessageService.read(clientId);
    }

    @PostMapping
    public ResponseEntity<SecretMessage> handleFileUpload(@RequestBody HashMap<String,String> map) throws Exception {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String message = map.get("message");
        return new ResponseEntity<>(secretMessageService.save(clientId, message), HttpStatus.CREATED);
    }
}
