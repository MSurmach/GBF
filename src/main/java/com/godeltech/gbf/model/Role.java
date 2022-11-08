package com.godeltech.gbf.model;

import lombok.Getter;

import static com.godeltech.gbf.model.State.FORM;
import static com.godeltech.gbf.model.State.OFFERS;

public enum Role {
    COURIER(FORM), CLIENT(FORM),
    REGISTRATIONS_VIEWER(OFFERS), REQUESTS_VIEWER(OFFERS);
    @Getter
    private final State firstState;

    Role(State firstState) {
        this.firstState = firstState;
    }
}
