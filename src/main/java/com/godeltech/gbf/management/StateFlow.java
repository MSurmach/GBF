package com.godeltech.gbf.management;

import com.godeltech.gbf.LocalMessageSource;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.management.State.*;

@Getter
public enum StateFlow implements LocalMessage {
    COURIER(COUNTRY_TO, CITY_FROM, DATE_FROM, COUNTRY_TO, CITY_TO, DATE_TO, CARGO_MENU, COMMENT, CONFIRMATION),
    CUSTOMER(COUNTRY_TO, CITY_FROM, DATE_FROM, COUNTRY_TO, CITY_TO, DATE_TO, CARGO_MENU, CONFIRMATION, USERS_LIST),
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

    @Override
    public String getLocalMessage(LocalMessageSource localMessageSource, String... args) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
