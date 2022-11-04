package com.godeltech.gbf.gui.message;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;

public interface MessageType {
    State getState();

    String getMessage(SessionData sessionData);
}
