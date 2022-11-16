package com.godeltech.gbf.gui.text_message;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;

public interface TextMessageType {
    State getState();

    String getMessage(SessionData sessionData);
}
