package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class ConfirmKeyboard extends LocaleKeyboard {
    public ConfirmKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        var confirmButton = new InlineKeyboardButton(localeMessageSource.getLocaleMessage("confirm"));
        confirmButton.setCallbackData(BotState.CONFIRM.name());
        List<InlineKeyboardButton> row = List.of(confirmButton);
        return new InlineKeyboardMarkup(List.of(row));
    }
}
