package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

import static com.godeltech.gbf.model.State.*;

public enum FormButton implements BotButton {
    ADD_ROUTE(CITY), EDIT_ROUTE(CITY),
    ADD_DATES(DATE), EDIT_DATES(DATE),
    ADD_SEATS(SEATS), EDIT_SEATS(SEATS),
    ADD_DELIVERY(DELIVERY), EDIT_DELIVERY(DELIVERY),
    ADD_COMMENT(COMMENT), EDIT_COMMENT(COMMENT),
    FORM_CONFIRM_AS_COURIER(SUCCESS_REGISTRATION),
    FORM_CONFIRM_AS_CLIENT(COURIERS_LIST_RESULT),
    FORM_CONFIRM_AS_REGISTRATION_VIEWER(REGISTRATIONS),
    FORM_CONFIRM_AS_REQUEST_VIEWER(REQUESTS);

    @Getter
    private final State nextState;

    FormButton(State nextState) {
        this.nextState = nextState;
    }
}
