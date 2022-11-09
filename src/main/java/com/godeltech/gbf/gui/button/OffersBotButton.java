package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;

import static com.godeltech.gbf.model.State.*;

public enum OffersBotButton implements BotButton {
    OFFER_EDIT(FORM),
    OFFER_DELETE(MY_OFFERS),
    OFFER_FIND_CLIENTS(CLIENTS_SEARCH_RESULT),
    OFFER_FIND_COURIERS(COURIERS_SEARCH_RESULT);
    final State nextState;

    OffersBotButton(State nextState) {
        this.nextState = nextState;
    }

    @Override
    public State getNextState() {
        return nextState;
    }
}
