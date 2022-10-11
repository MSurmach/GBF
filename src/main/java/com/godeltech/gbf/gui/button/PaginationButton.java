package com.godeltech.gbf.gui.button;

public enum PaginationButton implements BotButton{
    START("<<"), PREVIOUS("<"), PAGE("-%d-"), NEXT(">"), END(">>");
    public final String label;

    PaginationButton(String label) {
        this.label = label;
    }
}
