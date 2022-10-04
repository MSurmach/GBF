package com.godeltech.gbf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_data", schema = "public")
public class UserData {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "telegramUserId", nullable = false)
    private long telegramUserId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "country_from", nullable = false)
    private String countryFrom;

    @Column(name = "country_to", nullable = false)
    private String countryTo;

    @Column(name = "city_from", nullable = false)
    private String cityFrom;

    @Column(name = "city_to", nullable = false)
    private String cityTo;

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "documents")
    private boolean documents;

    @Column(name = "package")
    private String packageSize;

    @Column(name = "companions")
    private int companionCount;

    @Column(name = "comment")
    private String comment;

    @Transient
    private LinkedList<State> stateHistory = new LinkedList<>();
    @Transient
    private LinkedList<String> callbackHistory = new LinkedList<>();

    @Transient
    private List<UserData> registrations;

    @Transient
    private List<UserData> foundUsers;

    @Transient
    private Role role;

    @Transient
    private String callbackQueryId;

    public UserData(Long telegramUserId, String username) {
        this.telegramUserId = telegramUserId;
        this.username = username;
    }

    public void reset() {
        countryFrom = null;
        countryTo = null;
        cityFrom = null;
        cityTo = null;
        dateFrom = null;
        dateTo = null;
        documents = false;
        companionCount = 0;
        comment = null;
        stateHistory.clear();
        callbackHistory.clear();
        registrations = null;
        foundUsers = null;
        role = null;
        callbackQueryId = null;
    }
}
