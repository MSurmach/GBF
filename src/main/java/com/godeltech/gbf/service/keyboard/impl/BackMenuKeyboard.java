package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.NavigationBotButton.MENU;

@Service
@AllArgsConstructor
public class BackMenuKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String menuLabel = MENU.localLabel(localMessageSource);
        String menuCallback = MENU.name();
        var menuButton = KeyboardUtils.createButton(menuLabel, menuCallback);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}
