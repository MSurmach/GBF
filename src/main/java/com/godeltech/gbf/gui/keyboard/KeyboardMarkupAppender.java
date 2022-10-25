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
        if (keyboardMarkup == null) {
            keyboardMarkup = appendix;
            return this;
        }
        join(keyboardMarkup, appendix);
        return this;
    }

    public InlineKeyboardMarkup result() {
        return keyboardMarkup;
    }

    private void join(InlineKeyboardMarkup target, InlineKeyboardMarkup given) {
        given.getKeyboard().forEach(buttonRow -> target.getKeyboard().add(buttonRow));
    }
}
