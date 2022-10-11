package com.godeltech.gbf.model.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer countryId;

    @Column(nullable = false)
    private String name;

    public Country(String name) {
        this.name = name;
    }
}
