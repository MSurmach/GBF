package com.godeltech.gbf.model.db;

import com.godeltech.gbf.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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
    @OrderBy("orderNumber")
    private List<RoutePoint> routePoints;

    @Column
    private String comment;

    @Column
    private boolean documentsExist;

    @Column
    private int packageSize;

    @Column
    private int companionCount;

    @Enumerated(EnumType.STRING)
    @Column
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
