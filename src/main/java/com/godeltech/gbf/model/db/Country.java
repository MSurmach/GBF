package com.godeltech.gbf.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer countryId;

    @Column(nullable = false)
    private String name;
}
