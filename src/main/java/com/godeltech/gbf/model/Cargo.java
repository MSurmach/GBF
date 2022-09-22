package com.godeltech.gbf.model;

import com.godeltech.gbf.LocalMessageSource;
import lombok.Getter;

@Getter
public enum Cargo {
    DOCUMENTS("Documents"),
    PACKAGE("Package"),
    PEOPLE("People");

    private final String description;

    Cargo(String description) {
        this.description = description;
    }

    public String getLocalDescription(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(description);
    }
}
