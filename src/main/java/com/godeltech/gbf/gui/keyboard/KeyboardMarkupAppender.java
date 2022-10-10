package com.godeltech.gbf.gui.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class KeyboardMarkupAppender {

    InlineKeyboardMarkup keyboardMarkup;

    public KeyboardMarkupAppender(InlineKeyboardMarkup keyboardMarkup) {
        this.keyboardMarkup = keyboardMarkup;
    }

    public KeyboardMarkupAppender() {
    }

    public KeyboardMarkupAppender append(InlineKeyboardMarkup appendix) {
        if (keyboardMarkup == null) keyboardMarkup = appendix;
        else keyboardMarkup = join(keyboardMarkup, appendix);
        return this;
    }

    public InlineKeyboardMarkup result() {
        return keyboardMarkup;
    }

    private InlineKeyboardMarkup join(InlineKeyboardMarkup first, InlineKeyboardMarkup second) {
        second.getKeyboard().forEach(row -> first.getKeyboard().add(row));
        return first;
    }
}
