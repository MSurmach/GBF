package com.godeltech.gbf.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.model.State.*;

@Getter
public enum StateFlow {
    COURIER(COUNTRY_FROM, CITY_FROM, DATE_FROM, COUNTRY_TO, CITY_TO, DATE_TO, LOAD, CONFIRM, SUCCESS),
    CUSTOMER(COUNTRY_FROM, CITY_FROM, COUNTRY_TO, CITY_TO, LOAD, CONFIRM, USERS_LIST),
    VIEWER(REGISTRATIONS);

    private final List<State> flow;

    StateFlow(State... steps) {
        flow = Arrays.asList(steps);
    }

    public State getNextState(State state) {
        int currentBotStateIndex = this.flow.indexOf(state);
        return currentBotStateIndex == this.flow.size() - 1 ? MENU : flow.get(++currentBotStateIndex);
    }

    public State getFirstState() {
        return this.flow.get(0);
    }
}
