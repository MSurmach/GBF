package com.godeltech.gbf.service.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.message.Answer;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CargoPackageAnswer implements Answer {

    public final static String CARGO_PACKAGE_CODE = "cargo.package";
    private final LocalMessageSource localMessageSource;

    public CargoPackageAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getBotMessage(UserData userData, List<UserData>... users) {
        return localMessageSource.getLocaleMessage(CARGO_PACKAGE_CODE);
    }
}
