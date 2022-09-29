package com.godeltech.gbf.model;

import lombok.Getter;

import static com.godeltech.gbf.model.State.COUNTRY_FROM;
import static com.godeltech.gbf.model.State.REGISTRATIONS;

public enum Role {
    COURIER(COUNTRY_FROM), CUSTOMER(COUNTRY_FROM), REGISTRATIONS_VIEWER(REGISTRATIONS);
    @Getter
    private final State firstState;

    Role(State firstState) {
        this.firstState = firstState;
    }
}
