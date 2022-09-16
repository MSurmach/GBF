package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum CalendarButtons {
    PREV_MONTH("<<"), NEXT_MONTH(">>");
    final String callback;

    CalendarButtons(String callback) {
        this.callback = callback;
    }
}
