package com.godeltech.gbf.model.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table
public class RoutePoint {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
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
    private int orderNumber;

    public RoutePoint(Status status) {
        this.status = status;
    }

    public boolean isEmpty() {
        return country == null && city == null && visitDate == null;
    }
}
