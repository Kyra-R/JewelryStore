package com.rschir.prac.repositories;

import com.rschir.prac.model.JewelryBlob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JewelryBlobRepository extends JpaRepository<JewelryBlob, Long> {
}
