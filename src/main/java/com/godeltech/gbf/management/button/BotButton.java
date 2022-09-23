package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum BotButton implements ButtonLocalLabel {
    COURIER, CUSTOMER, REGISTRATIONS, IGNORE,
    GLOBAL_BACK, LOCAL_BACK, CONFIRM, MENU_BACK;

    @Override
    public String getLocalLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }

    public enum Calendar implements ButtonLocalLabel {
        INIT, PREVIOUS, NEXT,
        CHANGE_YEAR, SELECT_YEAR,
        CHANGE_MONTH, SELECT_MONTH,
        SELECT_DAY;

        @Override
        public String getLocalLabel(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }

    }

    public enum Cargo implements ButtonLocalLabel {
        INIT, SELECT_DOCUMENTS, CANCEL_DOCUMENTS,
        SELECT_PACKAGE, CANCEL_PACKAGE, EDIT_PACKAGE,
        SELECT_PEOPLE, CANCEL_PEOPLE, EDIT_PEOPLE,
        CONFIRM_CARGO;

        @Override
        public String getLocalLabel(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }
}
