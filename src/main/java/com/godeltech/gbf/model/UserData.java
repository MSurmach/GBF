package com.godeltech.gbf.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String yearFrom;
    private String monthFrom;
    private String dayFrom;
    private List<Cargo> cargo;
    private BotState botState;
    private BotStateFlow botStateFlow;
}
