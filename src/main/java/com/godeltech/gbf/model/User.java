package com.godeltech.gbf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.Month;

@Data
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;
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
        userId = userData.getId();
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
}
