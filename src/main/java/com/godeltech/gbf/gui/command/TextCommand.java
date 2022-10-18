package com.godeltech.gbf.gui.command;

public enum TextCommand {
    START("/start");
    private final String description;

    TextCommand(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}