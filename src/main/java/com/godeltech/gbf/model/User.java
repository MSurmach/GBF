package com.godeltech.gbf.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Data
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
    private List<Cargo> cargoList;

    public User(UserData userData) {
        userId = userData.getId();
        countryFrom = userData.getCountryFrom();
        cityFrom = userData.getCityFrom();
        countryTo = userData.getCountryTo();
        cityTo = userData.getCityTo();
        cargoList = userData.getCargo();
    }

    private LocalDate localDate(String year, String month, String day) {
        int yearAsInt = Integer.parseInt(year);
        return null;
    }
}
