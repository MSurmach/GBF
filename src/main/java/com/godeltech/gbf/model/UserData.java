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
    private LocalDate dateTo;
    private LocalDate dateFrom;
    private Load load;
    private State currentState;
    private State previousState;
    private StateFlow stateFlow;
}
