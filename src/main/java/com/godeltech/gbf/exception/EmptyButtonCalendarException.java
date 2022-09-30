package com.godeltech.gbf.exception;

import lombok.Getter;

public class EmptyButtonCalendarException extends RuntimeException {
    @Getter
    private String buttonCallback;
    @Getter
    private String callbackQueryId;

    public EmptyButtonCalendarException(String buttonCallback, String callbackQueryId) {
        this.buttonCallback = buttonCallback;
        this.callbackQueryId = callbackQueryId;
    }
}
