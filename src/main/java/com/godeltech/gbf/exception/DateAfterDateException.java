package com.godeltech.gbf.exception;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateAfterDateException extends RuntimeException {
    private String callbackQueryId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public DateAfterDateException(LocalDate dateFrom, LocalDate dateTo, String callbackQueryId) {
        this.callbackQueryId = callbackQueryId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
