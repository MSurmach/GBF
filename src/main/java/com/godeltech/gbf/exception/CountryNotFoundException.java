package com.godeltech.gbf.exception;

import lombok.Getter;

@Getter
public class CountryNotFoundException extends RuntimeException {
    private final String callbackQueryId;

    public CountryNotFoundException(String callbackQueryId) {
        this.callbackQueryId = callbackQueryId;
    }
}
