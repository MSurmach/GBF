package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CargoPeopleMessage implements Message {

    private final static String CARGO_PEOPLE_COURIER_CODE = "cargo.people.courier";
    private final static String CARGO_PEOPLE_CLIENT_CODE = "cargo.people.client";
    private final LocalMessageSource lms;

    @Override
    public String getMessage(UserData userData) {
        return userData.getRole() == Role.COURIER ?
                lms.getLocaleMessage(CARGO_PEOPLE_COURIER_CODE) :
                lms.getLocaleMessage(CARGO_PEOPLE_CLIENT_CODE);
    }
}
