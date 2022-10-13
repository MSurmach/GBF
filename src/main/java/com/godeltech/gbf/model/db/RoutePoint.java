package com.godeltech.gbf.model.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ROUTE_POINT")
public class RoutePoint {
    @Id
    @Column(name = "ROUTE_POINT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long routePointId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private TelegramUser telegramUser;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private Country country;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column
    private LocalDate visitDate;

    @Column
    private int orderNumber;

    public RoutePoint(Status status) {
        this.status = status;
    }

    public boolean isEmpty() {
        return country == null && city == null && visitDate == null;
    }
}
