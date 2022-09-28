package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
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

import static com.godeltech.gbf.management.button.BotButton.Comment.COMMENT_CONFIRM;
import static com.godeltech.gbf.management.button.BotButton.Comment.COMMENT_EDIT;

@Service
@AllArgsConstructor
public class CommentConfirmationKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String confirmLabel = COMMENT_CONFIRM.getLocalMessage(localMessageSource);
        String confirmCallback = COMMENT_CONFIRM.name();
        var confirmButton = KeyboardUtils.createButton(confirmLabel, confirmCallback);

        String editLabel = COMMENT_EDIT.getLocalMessage(localMessageSource);
        String editCallback = COMMENT_EDIT.name();
        var editButton = KeyboardUtils.createButton(editLabel, editCallback);

        List<InlineKeyboardButton> row = List.of(confirmButton, editButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        InlineKeyboardMarkup commentKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(commentKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
