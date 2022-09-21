package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum CalendarCommand {
    INIT("INIT"),
    PREV("<<"),
    NEXT(">>"),
    YEAR("YEAR"),
    RETURNEDYEAR("RETURNEDYEAR"),
    MONTH("MONTH"),
    RETURNEDMONTH("RETURNEDMONTH"),
    DAY("DAY");
    final String description;

    CalendarCommand(String description) {
        this.description = description;
    }
}
