package com.godeltech.gbf.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CITY")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cityId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    public City(String name) {
        this.name = name;
    }
}
