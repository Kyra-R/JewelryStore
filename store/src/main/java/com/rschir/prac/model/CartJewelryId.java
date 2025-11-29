package com.rschir.prac.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class CartJewelryId implements Serializable {
    private Long clientId;
    private Long jewelryId;
}
