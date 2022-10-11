package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoutePointFormMessage implements Message {
    private final LocalMessageSource lms;

    @Override
    public String getMessage(UserData userData) {
        return "test";
    }
}
