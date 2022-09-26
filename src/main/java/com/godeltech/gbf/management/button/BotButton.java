package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.LocalMessage;

public enum BotButton implements LocalMessage {
    COURIER, CUSTOMER, REGISTRATIONS, IGNORE,
    GLOBAL_BACK, LOCAL_BACK, CONFIRM, MENU_BACK;

    @Override
    public String getLocalMessage(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }

    public enum Calendar implements LocalMessage {
        INIT, PREVIOUS, NEXT,
        CHANGE_YEAR, SELECT_YEAR,
        CHANGE_MONTH, SELECT_MONTH,
        SELECT_DAY;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }

    }

    public enum Cargo implements LocalMessage {
        INIT, SELECT_DOCUMENTS, CANCEL_DOCUMENTS,
        SELECT_PACKAGE, CANCEL_PACKAGE, EDIT_PACKAGE,
        SELECT_PEOPLE, CANCEL_PEOPLE, EDIT_PEOPLE,
        CONFIRM_CARGO;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }
}
