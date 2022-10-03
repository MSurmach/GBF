package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum CargoBotButton implements BotButton {
    SELECT_DOCUMENTS, CANCEL_DOCUMENTS,
    SELECT_PACKAGE, CANCEL_PACKAGE, EDIT_PACKAGE,
    SELECT_PEOPLE, CANCEL_PEOPLE, EDIT_PEOPLE,
    CONFIRM_CARGO;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
