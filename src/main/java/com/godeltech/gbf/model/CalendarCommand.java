package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum CalendarCommand {
    PREV_MONTH("<<"), NEXT_MONTH(">>"), MONTH("MONTH"), DAY("DAY");
    final String callback;

    CalendarCommand(String callback) {
        this.callback = callback;
    }
}
