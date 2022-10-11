package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class CargoPackageMessage implements Message {

    public final static String CARGO_PACKAGE_CODE = "cargo.package";
    private final LocalMessageSource localMessageSource;

    public CargoPackageMessage(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(CARGO_PACKAGE_CODE);
    }
}
