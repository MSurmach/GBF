package com.godeltech.gbf.model;

import com.godeltech.gbf.LocalMessageSource;
import lombok.Getter;

@Getter
public enum Cargo {
    DOCUMENTS,
    PACKAGE,
    PEOPLE;

    public String getLocalDescription(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
