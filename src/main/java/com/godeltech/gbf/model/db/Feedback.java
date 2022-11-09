package com.godeltech.gbf.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Feedback {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private String content;
}
