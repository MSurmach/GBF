package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum BotButton implements LocalBotButton {
    COURIER, CUSTOMER, REGISTRATIONS_VIEWER,
    GLOBAL_BACK, LOCAL_BACK, CONFIRMATION, MENU;

    @Override
    public String getLocalMessage(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }

    public enum Calendar implements LocalBotButton {
        INIT, PREVIOUS, NEXT, IGNORE,
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

    public enum Registration implements LocalBotButton {
        REGISTRATION_EDIT, REGISTRATION_DELETE;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }

    public enum RegistrationEditor implements LocalBotButton {
        EDIT_COUNTRY_CITY_FROM, EDIT_COUNTRY_CITY_TO,
        EDIT_DATE_FROM, EDIT_DATE_TO,
        EDIT_COMMENT, EDIT_CARGO, EDIT_CONFIRM;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }

    public enum DateQuiz implements LocalBotButton {
        SELECT_DATE, SKIP_DATE;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }

    public enum FindCourier implements LocalBotButton {
        LOOK_AT_COURIERS;

        @Override
        public String getLocalMessage(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }
    }
}
