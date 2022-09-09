package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.LocaleMessageSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CargoKeyboard implements Keyboard {

    private LocaleMessageSource localeMessageSource;

    public CargoKeyboard(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Override
    public InlineKeyboardMarkup getKeyBoardMarkup() {
        return null;
    }
}
