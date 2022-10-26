package com.godeltech.gbf.gui.keyboard;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.NavigationBotButton.GLOBAL_BACK;
import static com.godeltech.gbf.gui.button.NavigationBotButton.MENU;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class ControlKeyboard {
    private LocalMessageSource lms;

    public InlineKeyboardMarkup controlMarkup() {
        var backButton = createLocalButton(GLOBAL_BACK, lms);
        var menuButton = createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(backButton, menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}

