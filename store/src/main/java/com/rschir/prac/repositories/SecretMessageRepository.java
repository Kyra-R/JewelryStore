package com.rschir.prac.repositories;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.model.SecretMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretMessageRepository extends JpaRepository<SecretMessage, Long> {

}
