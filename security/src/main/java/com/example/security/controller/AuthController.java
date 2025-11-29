package com.example.security.controller;

import com.example.security.dto.AuthenticationDTO;
import com.example.security.dto.ClientDTO;
import com.example.security.models.Client;
import com.example.security.security.JWTUtil;
import com.example.security.service.ClientsService;
import com.example.security.util.ErrorResponse;
import com.example.security.util.LoginAlreadyDefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientsService clientsService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(ClientsService clientsService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.clientsService = clientsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody ClientDTO clientDTO) {
        Client client = convertToClient(clientDTO);
        if(Objects.equals(client.getPassword(), "") || Objects.equals(client.getLogin(), "")) {
            return Map.of("message", "Forbidden data!");
        }
        clientsService.register(client);
        String token = jwtUtil.generateToken(client.getLogin(), client.getRole(), client.getClientId());
        return Map.of(
                "jwt-token", token,
                "name", client.getName()
        );
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(),
                        authenticationDTO.getPassword());
        if(Objects.equals(authenticationDTO.getPassword(), "") || Objects.equals(authenticationDTO.getLogin(), "")) {
            return Map.of("message", "No data!");
        }

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        Client client = clientsService.readByLogin(authenticationDTO.getLogin());

        System.out.println(client.getRole());

        String token = jwtUtil.generateToken(authenticationDTO.getLogin(), client.getRole(), client.getClientId());
        return Map.of(
                "jwt-token", token,
                "name", client.getName()
        );
    }


    @GetMapping
    public List<Client> GetClients() {
        return clientsService.findAllClientId();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(LoginAlreadyDefinedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public Client convertToClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setLogin(clientDTO.getLogin());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());
        return client;
    }
}
