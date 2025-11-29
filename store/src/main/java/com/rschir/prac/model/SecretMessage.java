package com.rschir.prac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "secret_message")
@Getter
@Setter
public class SecretMessage {
    @Id
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "message")
    private String message;

}
