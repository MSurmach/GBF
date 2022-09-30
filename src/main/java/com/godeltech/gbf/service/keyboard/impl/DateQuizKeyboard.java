package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.BotButton.DateQuiz.SELECT_DATE;
import static com.godeltech.gbf.management.button.BotButton.DateQuiz.SKIP_DATE;

@Service
@AllArgsConstructor
public class DateQuizKeyboard implements Keyboard {

    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String selectDateLabel = SELECT_DATE.getLocalMessage(localMessageSource);
        String selectDateCallback = SELECT_DATE.name();
        var selectDateButton = KeyboardUtils.createButton(selectDateLabel, selectDateCallback);

        String skipDateLabel = SKIP_DATE.getLocalMessage(localMessageSource);
        String skipDateCallback = SKIP_DATE.name();
        var skipDateButton = KeyboardUtils.createButton(skipDateLabel, skipDateCallback);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(selectDateButton));
        keyboard.add(List.of(skipDateButton));
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
