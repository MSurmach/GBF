package com.godeltech.gbf.gui.message;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;

public interface MessageType {
    State getState();

    String getMessage(UserData userData);

    default String initialMessage(UserData userData) {
        return "";
    }
}
