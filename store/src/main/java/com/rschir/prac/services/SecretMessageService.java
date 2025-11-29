package com.rschir.prac.services;

import com.rschir.prac.model.SecretMessage;
import com.rschir.prac.repositories.SecretMessageRepository;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecretMessageService {
    private final SecretMessageRepository secretMessageRepository;

    @Autowired
    public SecretMessageService(SecretMessageRepository secretMessageRepository) {
        this.secretMessageRepository = secretMessageRepository;
    }


    @Transactional
    public SecretMessage read(long clientId) {

        Optional<SecretMessage> secretMessage = secretMessageRepository.findById(clientId);
        if(secretMessage.isPresent()){
            return secretMessageRepository.findById(clientId).orElseThrow(NotFoundException::new);
        } else {
            SecretMessage newSecretMessage = new SecretMessage();
            newSecretMessage.setMessage("NULL");
            newSecretMessage.setClientId(clientId);
            return newSecretMessage;
        }
    }

    @Transactional
    public SecretMessage save(long clientId, String message) {
        SecretMessage secretMessage = new SecretMessage();
        secretMessage.setClientId(clientId);
        secretMessage.setMessage(message);
        //System.out.println(message);
        return secretMessageRepository.save(secretMessage);
    }

}
