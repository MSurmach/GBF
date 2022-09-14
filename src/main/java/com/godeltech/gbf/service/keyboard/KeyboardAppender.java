package com.godeltech.gbf.service.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class KeyboardAppender {

    InlineKeyboardMarkup keyboard;

    public KeyboardAppender(InlineKeyboardMarkup keyboard) {
        this.keyboard = keyboard;
    }

    public KeyboardAppender() {
    }

    public KeyboardAppender append(InlineKeyboardMarkup appendix) {
        if (keyboard == null) keyboard = appendix;
        else keyboard = join(keyboard, appendix);
        return this;
    }

    public InlineKeyboardMarkup result() {
        return keyboard;
    }

    private InlineKeyboardMarkup join(InlineKeyboardMarkup first, InlineKeyboardMarkup second) {
        second.getKeyboard().forEach(row -> first.getKeyboard().add(row));
        return first;
    }
}
