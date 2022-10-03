package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.management.button.NavigationBotButton.GLOBAL_BACK;
import static com.godeltech.gbf.management.button.NavigationBotButton.MENU;

@Service
@AllArgsConstructor
public class ControlKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String backLabel = GLOBAL_BACK.localLabel(localMessageSource);
        String backCallback = GLOBAL_BACK.name();
        var backButton = KeyboardUtils.createButton(backLabel, backCallback);

        String menuLabel = MENU.localLabel(localMessageSource);
        String menuCallback = MENU.name();
        var menuButton = KeyboardUtils.createButton(menuLabel, menuCallback);

        List<InlineKeyboardButton> buttons = List.of(backButton, menuButton);
        return new InlineKeyboardMarkup(List.of(buttons));
    }
}

