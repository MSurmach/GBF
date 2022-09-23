package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.controls.Command;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.controls.Command.GLOBAL_BACK;
import static com.godeltech.gbf.controls.Command.MENU_BACK;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class ControlKeyboard extends LocaleKeyboard {

    public ControlKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String backLabel = GLOBAL_BACK.getLocalDescription(localMessageSource);
        String backCallback = GLOBAL_BACK.name();
        var backButton = createButton(backLabel, backCallback);

        String menuLabel = MENU_BACK.getLocalDescription(localMessageSource);
        String menuCallback = MENU_BACK.name();
        var menuButton = createButton(menuLabel, menuCallback);

        List<InlineKeyboardButton> buttons = List.of(backButton, menuButton);
        return new InlineKeyboardMarkup(List.of(buttons));
    }
}

