package com.godeltech.gbf.service.text;

import com.godeltech.gbf.model.UserData;

public interface Text {
    String getText(UserData userData);

    default String initialMessage(UserData userData) {
        return "";
    }
}
