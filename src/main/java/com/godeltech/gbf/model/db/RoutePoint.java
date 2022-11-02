package com.godeltech.gbf.model.db;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @ManyToOne
    private City city;

    @Column
    private int orderNumber;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    @ToString.Exclude
    private Offer offer;

    public RoutePoint(City city) {
        this.city = city;
    }

    public RoutePoint(Status status, City city) {
        this.city = city;
        this.status = status;
    }
}
