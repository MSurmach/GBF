package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

import static com.godeltech.gbf.model.State.*;

@Getter
public enum MenuButton implements BotButton {
    START_AS_COURIER(FORM), START_AS_CLIENT(FORM), LOOK_AT_MY_REGISTRATIONS(MY_OFFERS), LOOK_AT_MY_REQUESTS(MY_OFFERS), LOOK_AT_ALL_REGISTRATIONS(ALL_OFFERS), LOOK_AT_ALL_REQUESTS(ALL_OFFERS), SEND_FEEDBACK(FEEDBACK), ADMIN_PANEL(ADMIN_INSTRUMENTS), SWITCH_LANGUAGE(LANGUAGE);
    final State nextState;

    MenuButton(State nextState) {
        this.nextState = nextState;
    }
}
