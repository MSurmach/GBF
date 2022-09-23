package com.godeltech.gbf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courier", schema = "public")
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
    @Column(name = "documents")
    private Boolean documents;
    @Column(name = "companions")
    private int companionCount;
    @Column(name = "package")
    private String cargo;

    public User(UserData userData) {
        telegramId = userData.getId();
        username = userData.getUsername();
        countryFrom = userData.getCountryFrom();
        cityFrom = userData.getCityFrom();
        countryTo = userData.getCountryTo();
        cityTo = userData.getCityTo();
        dateFrom = userData.getDateFrom();
        dateTo = userData.getDateTo();
        documents = userData.getDocuments();
        companionCount = userData.getCompanionCount();
        cargo = userData.getPackageSize();
    }
}
