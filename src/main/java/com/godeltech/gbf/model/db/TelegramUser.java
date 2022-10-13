package com.godeltech.gbf.model.db;

import com.godeltech.gbf.model.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TELEGRAM_USER")
@Builder
@AllArgsConstructor
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

    public TelegramUser() {

    }
}
