package com.godeltech.gbf.management.button;


import com.godeltech.gbf.LocalMessageSource;

public enum RequestButton implements BotButton {

    REQUEST_EDIT, REQUEST_DELETE, REQUEST_FIND_COURIERS;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
