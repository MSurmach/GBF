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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
