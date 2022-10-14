package com.godeltech.gbf.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    private Integer countryId;

    @Column(nullable = false)
    private String name;
}
