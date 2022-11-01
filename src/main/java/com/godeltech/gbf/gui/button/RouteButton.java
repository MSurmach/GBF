package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;

import static com.godeltech.gbf.model.State.FORM;
import static com.godeltech.gbf.model.State.ROUTE;

public enum RouteButton implements BotButton {
    SELECT_CITY(ROUTE), CONFIRM_ROUTE(FORM), CLEAR_ROUTE(ROUTE);

    private final State nextState;

    RouteButton(State nextState) {
        this.nextState = nextState;
    }

    @Override
    public State getNextState(){
        return nextState;
    }
}
