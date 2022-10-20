package com.godeltech.gbf.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class RoutePoint {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @ManyToOne
    private TelegramUser telegramUser;

    @ManyToOne
    private Country country;
    @ManyToOne
    private City city;
    @Column
    private LocalDate visitDate;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private int orderNumber;

    public RoutePoint(Status status) {
        this.status = status;
    }

    public boolean isEmpty() {
        return country == null && city == null && visitDate == null;
    }
}
