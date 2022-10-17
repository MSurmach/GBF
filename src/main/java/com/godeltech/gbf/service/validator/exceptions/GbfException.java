package com.godeltech.gbf.service.validator.exceptions;

import lombok.Getter;

@Getter
public class GbfException extends RuntimeException {
    private final String callbackQueryId;
    private final String alertMessage;

    public GbfException(String callbackQueryId, String alertMessage) {
        this.callbackQueryId = callbackQueryId;
        this.alertMessage = alertMessage;
    }
}
