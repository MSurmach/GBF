package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

import static com.godeltech.gbf.model.State.*;

public enum FormButton implements BotButton {
    ADD_ROUTE(ROUTE), EDIT_ROUTE(ROUTE),
    ADD_DATES(DATE), EDIT_DATES(DATE),
    ADD_SEATS(SEATS), EDIT_SEATS(SEATS),
    ADD_DELIVERY(DELIVERY), EDIT_DELIVERY(DELIVERY),
    ADD_COMMENT(COMMENT), EDIT_COMMENT(COMMENT),
    REGISTER(SUCCESS_REGISTRATION),
    SEARCH_CLIENTS(SEARCH_RESULT),
    SAVE_CHANGES(MY_OFFERS);

    @Getter
    private final State nextState;

    FormButton(State nextState) {
        this.nextState = nextState;
    }
}
