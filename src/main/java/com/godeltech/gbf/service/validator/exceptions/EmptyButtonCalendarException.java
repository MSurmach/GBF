package com.godeltech.gbf.service.validator.exceptions;

import lombok.Getter;

public class EmptyButtonCalendarException extends RuntimeException {
    @Getter
    private final String buttonCallback;
    @Getter
    private final String callbackQueryId;

    public EmptyButtonCalendarException(String buttonCallback, String callbackQueryId) {
        this.buttonCallback = buttonCallback;
        this.callbackQueryId = callbackQueryId;
    }
}
