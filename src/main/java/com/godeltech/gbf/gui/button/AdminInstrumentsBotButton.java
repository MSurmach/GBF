package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

@Getter
public enum AdminInstrumentsBotButton implements BotButton {
    LOOK_AT_FEEDBACKS(State.ALL_FEEDBACKS);
    final State nextState;

    AdminInstrumentsBotButton(State nextState) {
        this.nextState = nextState;
    }
}
