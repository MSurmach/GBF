package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
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
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(CARGO_PACKAGE_CODE);
    }
}
