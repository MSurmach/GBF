package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

import static com.godeltech.gbf.model.State.*;

public enum CalendarBotButton implements BotButton {
    PREVIOUS(DATE), NEXT(DATE), IGNORE(null),
    CHANGE_YEAR(YEAR), SELECT_YEAR(DATE),
    CHANGE_MONTH(MONTH), SELECT_MONTH(DATE),
    SELECT_DAY(DATE), CONFIRM_DATE(FORM),
    CLEAR_DATES(DATE);

    @Getter
    private final State nextState;

    CalendarBotButton(State nextState) {
        this.nextState = nextState;
    }
}
