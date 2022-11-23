package com.godeltech.gbf.gui.text_message;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Session;

public interface TextMessageType {
    State getState();

    String getMessage(Session session);
}
