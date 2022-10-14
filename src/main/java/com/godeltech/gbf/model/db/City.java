package com.godeltech.gbf.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CITY")
public class City {
    @Id
    private Integer cityId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
}
