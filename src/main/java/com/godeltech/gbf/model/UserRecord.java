package com.godeltech.gbf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_record", schema = "public")
public class UserRecord {
    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;

    @Column(name = "telegramUserId", nullable = false)
    private long telegramUserId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "country_from", nullable = false)
    private String countryFrom;

    @Column(name = "country_to", nullable = false)
    private String countryTo;

    @Column(name = "city_from")
    private String cityFrom;

    @Column(name = "city_to")
    private String cityTo;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "documents")
    private boolean documents;

    @Column(name = "package")
    private String packageSize;

    @Column(name = "companions")
    private int companionCount;

    @Column(name = "comment")
    private String comment;

    @Column(name = "changed_at")
    private LocalDate changedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    public UserRecord(UserData userData) {
        telegramUserId = userData.getTelegramUserId();
        username = userData.getUsername();
        countryFrom = userData.getCountryFrom();
        countryTo = userData.getCountryTo();
        cityFrom = userData.getCityFrom();
        cityTo = userData.getCityTo();
        dateTo = userData.getDateTo();
        dateFrom = userData.getDateFrom();
        documents = userData.isDocuments();
        packageSize = userData.getPackageSize();
        companionCount = userData.getCompanionCount();
        comment = userData.getComment();
        role = userData.getRole();
        changedAt = LocalDate.now();
    }
}
