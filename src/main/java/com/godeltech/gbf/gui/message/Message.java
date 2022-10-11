package com.godeltech.gbf.gui.message;

import com.godeltech.gbf.model.UserData;

public interface Message {
    String getMessage(UserData userData);

    default String initialMessage(UserData userData) {
        return "";
    }
}
