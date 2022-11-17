package com.godeltech.gbf.service.validator.exceptions;

import lombok.Getter;

@Getter
public class GbfAlertException extends RuntimeException {
    private final String callbackQueryId;
    private final String alertMessage;

    public GbfAlertException(String callbackQueryId, String alertMessage) {
        this.callbackQueryId = callbackQueryId;
        this.alertMessage = alertMessage;
    }
}
