package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.management.button.BotButton.GLOBAL_BACK;
import static com.godeltech.gbf.management.button.BotButton.MENU_BACK;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class ControlKeyboard extends LocaleKeyboard {

    public ControlKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String backLabel = GLOBAL_BACK.getLocalLabel(localMessageSource);
        String backCallback = GLOBAL_BACK.name();
        var backButton = KeyboardUtils.createButton(backLabel, backCallback);

        String menuLabel = MENU_BACK.getLocalLabel(localMessageSource);
        String menuCallback = MENU_BACK.name();
        var menuButton = KeyboardUtils.createButton(menuLabel, menuCallback);

        List<InlineKeyboardButton> buttons = List.of(backButton, menuButton);
        return new InlineKeyboardMarkup(List.of(buttons));
    }
}

