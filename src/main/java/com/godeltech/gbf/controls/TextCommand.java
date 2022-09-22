package com.godeltech.gbf.controls;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TextCommand {
    START("/start"), STOP("/stop"), HELP("/help");
    private final String text;

}
