package com.godeltech.gbf.controls;

import com.godeltech.gbf.LocalMessageSource;

public enum Command {
    //Initial commands
    COURIER, CUSTOMER, REGISTRATIONS,

    //Global commands
    GLOBAL_BACK, LOCAL_BACK, CONFIRM, MENU_BACK;

    public String getLocalDescription(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }

    public enum Text {
        START("/start"), STOP("/stop"), HELP("/help");
        private final String text;

        Text(String text) {
            this.text = text;
        }

        public String getText(){
            return text;
        }
    }

    public enum Calendar {
        INIT, PREVIOUS, NEXT,
        CHANGE_YEAR, SELECT_YEAR,
        CHANGE_MONTH, SELECT_MONTH,
        SELECT_DAY;

        public String getLocalDescription(LocalMessageSource localMessageSource) {
            return localMessageSource.getLocaleMessage(this.name());
        }

    }

    public enum Cargo {
        INIT, SELECT_DOCUMENTS,CANCEL_DOCUMENTS, SELECT_PACKAGE, SELECT_PEOPLE, CONFIRM_CARGO;
    }
}
