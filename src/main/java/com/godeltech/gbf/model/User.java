package com.godeltech.gbf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id")
    private Long telegramId;

    @Column(name = "username")
    private String username;
    @Column(name = "country_from")
    private String countryFrom;
    @Column(name = "city_from")
    private String cityFrom;
    @Column(name = "country_to")
    private String countryTo;
    @Column(name = "city_to")
    private String cityTo;
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;
    @Column(name = "cargo")
    private Load load;

    public User(UserData userData) {
        telegramId = userData.getId();
        username = userData.getUsername();
        countryFrom = userData.getCountryFrom();
        cityFrom = userData.getCityFrom();
        countryTo = userData.getCountryTo();
        cityTo = userData.getCityTo();
        load = userData.getLoad();
        dateFrom = mapLocalDate(userData.getYearFrom(), userData.getMonthFrom(), userData.getDayFrom());
        dateTo = mapLocalDate(userData.getYearTo(), userData.getYearTo(), userData.getDayTo());
    }

    private LocalDate mapLocalDate(String year, String monthName, String day) {
        int yearAsInt = Integer.parseInt(year);
        Month month = Month.valueOf(monthName);
        int dayAsInt = Integer.parseInt(day);
        return LocalDate.of(yearAsInt, month, dayAsInt);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDateTo = dateTo.format(dateTimeFormatter);
        String formattedDateFrom = dateFrom.format(dateTimeFormatter);
        return """
                @%s planned to visit %s, %s on %s, /
                starting point is %s, %s on %s.
                The load, which can be transferred: %s
                """.formatted(username,countryTo, cityTo, formattedDateTo,countryFrom, cityFrom, formattedDateFrom, load);
    }
}
