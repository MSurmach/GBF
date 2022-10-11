package com.godeltech.gbf.model.db;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "TELEGRAM_USER")
public class TelegramUser {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long telegramUserId;

    @Column
    private Long telegramId;

    @Column
    private String username;

    @OneToMany
    @JoinColumn(name = "ROUTE_POINT_ID")
    private List<RoutePoint> routePoints;

    @Column
    private String comment;
}
