package com.godeltech.gbf.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserData {
    private long id;
    private String username;
    private Country countryFrom;
    private Country countryTo;
    private Country cityFrom;
    private Country cityTo;
    private Date dateFrom;
    private Date dateTo;
    private List<Cargo> cargo;
    private BotState botState;
    private BotStateFlow botStateFlow;
}
