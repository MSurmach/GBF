package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.RoutePoint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.LinkedList;

@Data
@NoArgsConstructor
public class UserData {
    private Long recordId;
    private long telegramUserId;
    private String username;
    private String countryFrom;
    private String countryTo;
    private String cityFrom;
    private String cityTo;
    private LocalDate dateTo;
    private LocalDate dateFrom;
    private boolean documents;
    private String packageSize;
    private int companionCount;
    private String comment;

    private LinkedList<RoutePoint> routePoints = new LinkedList<>();
    private LinkedList<State> stateHistory = new LinkedList<>();
    private LinkedList<String> callbackHistory = new LinkedList<>();
    private Page<UserRecord> recordsPage;
    private int pageNumber;
    private Role role;
    private String callbackQueryId;
    private UserData tempForSearch;

    public UserData(Long telegramUserId, String username) {
        this.telegramUserId = telegramUserId;
        this.username = username;
    }

    public UserData(UserRecord userRecord) {
        this.copyDataRecord(userRecord);
    }

    public void copyDataRecord(UserRecord record) {
        telegramUserId = record.getTelegramUserId();
        recordId = record.getRecordId();
        username = record.getUsername();
        countryFrom = record.getCountryFrom();
        countryTo = record.getCountryTo();
        cityFrom = record.getCityFrom();
        cityTo = record.getCityTo();
        dateTo = record.getDateTo();
        dateFrom = record.getDateFrom();
        documents = record.isDocuments();
        packageSize = record.getPackageSize();
        companionCount = record.getCompanionCount();
        comment = record.getComment();
        role = record.getRole();
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
        recordsPage = null;
        role = null;
        callbackQueryId = null;
        pageNumber = 0;
        tempForSearch = null;
    }
}
