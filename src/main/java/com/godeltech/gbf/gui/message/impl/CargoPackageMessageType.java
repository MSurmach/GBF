package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class CargoPackageMessageType implements MessageType {

    public final static String CARGO_PACKAGE_CODE = "cargo.package";
    private final LocalMessageSource localMessageSource;

    public CargoPackageMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.CARGO_PACKAGE;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(CARGO_PACKAGE_CODE);
    }
}
