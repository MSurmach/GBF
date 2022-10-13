package com.godeltech.gbf.model.db;

import com.godeltech.gbf.model.Role;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "TELEGRAM_USER")
public class TelegramUser {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column
    private Long telegramId;

    @Column
    private String username;

    @OneToMany(mappedBy = "telegramUser")
    private List<RoutePoint> routePoints;

    @Column
    private String comment;

    @Column
    private boolean documentsExist;

    @Column
    private String packageSize;

    @Column
    private int companionCount;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private LocalDate changedAt;

    @Column
    private LocalDate expiredAt;
}
