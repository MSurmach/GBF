package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum BotButton implements LocalBotButton {
    COURIER, CUSTOMER, REGISTRATIONS, IGNORE,
    GLOBAL_BACK, LOCAL_BACK, CONFIRMATION, MENU_BACK;

    @Override
    public String getLocalMessage(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }

    public enum Calendar implements LocalBotButton {
        INIT, PREVIOUS, NEXT,
        CHANGE_YEAR, SELECT_YEAR,
        CHANGE_MONTH, SELECT_MONTH,
        SELECT_DAY;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }

    }

    public enum Cargo implements LocalBotButton {
        INIT, SELECT_DOCUMENTS, CANCEL_DOCUMENTS,
        SELECT_PACKAGE, CANCEL_PACKAGE, EDIT_PACKAGE,
        SELECT_PEOPLE, CANCEL_PEOPLE, EDIT_PEOPLE,
        CONFIRM_CARGO;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }

    public enum Comment implements LocalBotButton {
        COMMENT_YES, COMMENT_NO, COMMENT_CONFIRM, COMMENT_EDIT;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }
}
