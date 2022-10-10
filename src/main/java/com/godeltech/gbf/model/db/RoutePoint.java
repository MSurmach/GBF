package com.godeltech.gbf.model.db;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ROUTE_POINT")
public class RoutePoint {
    @Id
    @Column(name = "ROUTE_POINT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long routePointId;

    @ManyToOne
    @JoinColumn(name = "TELEGRAM_USER_ID")
    private TelegramUser telegramUser;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column
    private LocalDate visitDate;
}
