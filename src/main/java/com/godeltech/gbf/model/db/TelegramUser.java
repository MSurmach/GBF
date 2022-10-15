package com.godeltech.gbf.model.db;

import com.godeltech.gbf.model.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
public class TelegramUser {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long telegramId;

    @Column
    private String username;

    @OneToMany(
            mappedBy = "telegramUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
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

    public TelegramUser() {
    }

    public void addRoutePoint(RoutePoint routePoint) {
        routePoints.add(routePoint);
        routePoint.setTelegramUser(this);
    }

    public void removeRoutePoint(RoutePoint routePoint) {
        routePoints.remove(routePoint);
        routePoint.setTelegramUser(null);
    }
}
