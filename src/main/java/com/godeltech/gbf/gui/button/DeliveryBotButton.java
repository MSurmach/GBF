package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;
import lombok.Getter;

import static com.godeltech.gbf.model.State.FORM;

@Getter
public enum DeliveryBotButton implements BotButton {
    SELECT_XXS(FORM), SELECT_XS(FORM), SELECT_S(FORM), SELECT_M(FORM), SELECT_L(FORM);
    private final State nextState;

    DeliveryBotButton(State nextState) {
        this.nextState = nextState;
    }
}
