package com.godeltech.gbf.gui.button;

import com.godeltech.gbf.model.State;

public interface BotButton {
    String name();
    default State getNextState(){
        return null;
    }
}
