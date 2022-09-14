package com.godeltech.gbf.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.model.BotState.*;

@Getter
public enum BotStateFlow {

    COURIER(COUNTRY_FROM, CITY_FROM, COUNTRY_TO, CITY_TO, YEAR_FROM, MONTH_FROM, DAY_FROM, YEAR_TO, MONTH_TO, DAY_TO, LOAD, CONFIRM),
    CUSTOMER(COUNTRY_FROM, CITY_FROM, COUNTRY_TO, CITY_TO, LOAD, CONFIRM),
    VIEWER(REGISTRATIONS);

    private final List<BotState> flow;

    BotStateFlow(BotState... steps) {
        flow = Arrays.asList(steps);
    }

    public BotState getNextState(BotState botState) {
        int currentBotStateIndex = this.flow.indexOf(botState);
        return currentBotStateIndex == this.flow.size() - 1 ? MENU : flow.get(++currentBotStateIndex);
    }

    public BotState getPreviousState(BotState botState) {
        int currentBotStateIndex = this.flow.indexOf(botState);
        return currentBotStateIndex == 0 ? MENU : flow.get(--currentBotStateIndex);
    }

    public BotState getFirstState() {
        return this.flow.get(0);
    }
}
