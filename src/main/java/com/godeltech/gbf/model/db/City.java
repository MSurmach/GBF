package com.godeltech.gbf.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class City {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;
}
