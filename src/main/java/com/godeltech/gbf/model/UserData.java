package com.godeltech.gbf.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserData {
    private long id;
    private String username;
    private String countryFrom;
    private String countryTo;
    private String cityFrom;
    private String cityTo;
    private String yearTo;
    private String monthTo;
    private String dayTo;

    private LocalDate dateTo;
    private LocalDate dateFrom;
    private String yearFrom;
    private String monthFrom;
    private String dayFrom;
    private Load load;
    private BotState currentBotState;
    private BotState previousBotState;
    private BotStateFlow botStateFlow;

    @Override
    public String toString() {
        return """
                Starting point is %s, %s on %s %s %s, destination point is %s, %s on %s %s %s.
                Your load is %s.
                """.formatted(countryFrom, cityFrom, dayFrom, monthFrom, yearFrom, countryTo, cityTo, dayTo, monthTo, yearTo, load);
    }
}
