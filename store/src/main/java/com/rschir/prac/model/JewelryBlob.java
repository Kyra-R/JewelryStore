package com.rschir.prac.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jewelry_blob")
@Getter
@Setter
public class JewelryBlob {
    @Id
    @Column(name = "jewelry_id", nullable = false)
    private Long jewelryId;

    @OneToOne
    @MapsId
    private Jewelry jewelry;

    @Lob
    @Column(name = "pic", nullable = false)
    private byte[] pic;
}
