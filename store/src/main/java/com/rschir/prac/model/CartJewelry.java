package com.rschir.prac.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(CartJewelryId.class)
@Table(name = "cart_jewelry")
public class CartJewelry {

    @Id
    @Column(name = "client_id")
    private Long clientId;

    @Id
    @Column(name = "jewelry_id")
    private Long jewelryId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @MapsId
    @OneToOne
    @JoinColumn(name = "jewelry_id", nullable = false)
    private Jewelry jewelry;
}
