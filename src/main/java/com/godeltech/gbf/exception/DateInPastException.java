package com.godeltech.gbf.exception;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateInPastException extends RuntimeException {
    private final LocalDate invalidDate;
    private final LocalDate nowDate;

    private final String callbackQueryId;

    public DateInPastException(LocalDate invalidDate, LocalDate nowDate, String callbackQueryId) {
        this.invalidDate = invalidDate;
        this.nowDate = nowDate;
        this.callbackQueryId = callbackQueryId;
    }
}
