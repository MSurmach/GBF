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

    @OneToMany(mappedBy = "telegramUser")
    private List<RoutePoint> routePoints;
}
