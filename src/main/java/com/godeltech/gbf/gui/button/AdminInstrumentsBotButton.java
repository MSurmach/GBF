package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

import static com.godeltech.gbf.model.State.STATISTIC;

@Getter
public enum AdminInstrumentsBotButton implements BotButton {
    LOOK_AT_FEEDBACKS(State.ALL_FEEDBACKS), LOOK_AT_STATISTIC (STATISTIC);
    final State nextState;

    AdminInstrumentsBotButton(State nextState) {
        this.nextState = nextState;
    }
}
