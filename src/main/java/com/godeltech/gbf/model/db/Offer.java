package com.godeltech.gbf.model.db;

import com.godeltech.gbf.model.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "telegramUserId")
    private TelegramUser telegramUser;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @OneToMany(mappedBy = "offer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("orderNumber")
    @ToString.Exclude
    private List<RoutePoint> routePoints;


    @Column
    private String comment;

    @Column
    private Integer seats;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Delivery delivery;

    public void addRoutePoint(RoutePoint routePoint){
        routePoints.add(routePoint);
        routePoint.setOffer(this);
    }
}
