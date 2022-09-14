package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class ControlKeyboard extends LocaleKeyboard {

    public ControlKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        var backButton = new InlineKeyboardButton(localeMessageSource.getLocaleMessage("back"));
        backButton.setCallbackData(BotState.BACK.name());

        var menuButton = new InlineKeyboardButton(localeMessageSource.getLocaleMessage("main_menu"));
        menuButton.setCallbackData(BotState.MENU.name());

        List<InlineKeyboardButton> buttons = List.of(backButton, menuButton);
        return new InlineKeyboardMarkup(List.of(buttons));
    }
}

