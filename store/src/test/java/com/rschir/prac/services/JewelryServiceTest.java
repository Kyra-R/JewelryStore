package com.rschir.prac.services;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.model.ProductMaterial;
import com.rschir.prac.model.ProductType;
import com.rschir.prac.repositories.JewelryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

class JewelryServiceTest {


    @Test
    void typeFromString() throws Exception {
        assertEquals(ProductType.RING, JewelryService.typeFromString("RING"));
    }

    @Test
    void materialFromString() throws Exception {
        assertEquals(ProductMaterial.SILVER, JewelryService.materialFromString("SILVER"));
    }



}