package com.rschir.prac.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Table(name = "jewelry")
@Getter
@Setter
public class Jewelry {
    @Id
    @Column(name = "jewelry_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jewelryId;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count", nullable = false)
    private int count;


    @Enumerated(EnumType.STRING)
    @Column(name = "jewelry_type", nullable = false)
    private ProductType jewelryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "jewelry_material", nullable = false)
    private ProductMaterial jewelryMaterial;
}
