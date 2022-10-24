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

    @Enumerated(EnumType.STRING)
    @Column
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
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private int orderNumber;

    public RoutePoint(Status status) {
        this.status = status;
    }

    public boolean isEmpty() {
        return country == null &&
                city == null &&
                startDate == null &&
                endDate == null;
    }

    public boolean isTheSameGeographical(RoutePoint given) {
        boolean isTheSameCountry = this.country.equals(given.country);
        if (this.city == null || given.city == null) return isTheSameCountry;
        boolean isTheSameCity = this.city.equals(given.city);
        return isTheSameCountry && isTheSameCity;
    }
}
