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

import static com.godeltech.gbf.management.button.CommentBotButton.COMMENT_NO;
import static com.godeltech.gbf.management.button.CommentBotButton.COMMENT_YES;

@Service
@AllArgsConstructor
public class CommentKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String yesLabel = COMMENT_YES.localLabel(localMessageSource);
        String yesCallback = COMMENT_YES.name();
        var yesButton = KeyboardUtils.createButton(yesLabel, yesCallback);

        String noLabel = COMMENT_NO.localLabel(localMessageSource);
        String noCallback = COMMENT_NO.name();
        var noButton = KeyboardUtils.createButton(noLabel, noCallback);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(yesButton, noButton));
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
