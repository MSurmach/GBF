package com.godeltech.gbf.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TextCommand {
    START("/start"), STOP("/stop"), HELP("/help");
    private final String text;

}
