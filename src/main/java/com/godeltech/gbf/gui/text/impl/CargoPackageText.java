package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import org.springframework.stereotype.Component;

@Component
public class CargoPackageText implements Text {

    public final static String CARGO_PACKAGE_CODE = "cargo.package";
    private final LocalMessageSource localMessageSource;

    public CargoPackageText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(CARGO_PACKAGE_CODE);
    }
}
