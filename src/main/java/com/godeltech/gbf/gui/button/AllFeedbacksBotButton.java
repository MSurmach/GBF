package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;

import static com.godeltech.gbf.model.State.FEEDBACK_DELETE_BY_ID;

public enum AllFeedbacksBotButton implements BotButton{
    DELETE_BY_ID(FEEDBACK_DELETE_BY_ID);

    final State nextState;

    AllFeedbacksBotButton(State nextState) {
        this.nextState = nextState;
    }

    @Override
    public State getNextState() {
        return nextState;
    }
}
