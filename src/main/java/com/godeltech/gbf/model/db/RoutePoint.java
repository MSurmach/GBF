package com.godeltech.gbf.model.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
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
    private City city;

    @Column
    private int orderNumber;

    public RoutePoint(Status status) {
        this.status = status;
    }

    public RoutePoint(Status status, City city) {
        this.status = status;
        this.city = city;
    }
    public boolean isTheSameGeographical(RoutePoint given) {
        return this.city.equals(given.city);
    }
}
