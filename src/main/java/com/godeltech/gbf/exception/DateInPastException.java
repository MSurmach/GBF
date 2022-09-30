package com.godeltech.gbf.exception;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateInPastException extends RuntimeException {
    private LocalDate invalidDate;
    private LocalDate nowDate;

    private String callbackQueryId;

    public DateInPastException(LocalDate invalidDate, LocalDate nowDate, String callbackQueryId) {
        this.invalidDate = invalidDate;
        this.nowDate = nowDate;
        this.callbackQueryId = callbackQueryId;
    }
}
