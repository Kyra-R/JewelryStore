package com.example.security.repositories;

import com.example.security.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientsRepository extends CrudRepository<Client, String> {
}
