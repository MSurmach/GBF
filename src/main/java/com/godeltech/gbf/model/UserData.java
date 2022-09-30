package com.godeltech.gbf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "telegramUserId")
    private long telegramUserId;

    @Column(name = "username")
    private String username;

    @Column(name = "country_from")
    private String countryFrom;

    @Column(name = "country_to")
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

    @Transient
    private State currentState;

    @Transient
    private State previousState;

    @Transient
    private String callback;

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
}
