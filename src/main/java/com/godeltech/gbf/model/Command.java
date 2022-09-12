package com.godeltech.gbf.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Command {
    START("/start"), STOP("/stop");
    private final String text;

}
