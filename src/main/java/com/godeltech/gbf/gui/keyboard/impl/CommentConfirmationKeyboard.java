package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CommentBotButton.COMMENT_CONFIRM;
import static com.godeltech.gbf.gui.button.CommentBotButton.COMMENT_EDIT;

@Component
@AllArgsConstructor
public class CommentConfirmationKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String confirmLabel = COMMENT_CONFIRM.localLabel(localMessageSource);
        String confirmCallback = COMMENT_CONFIRM.name();
        var confirmButton = KeyboardUtils.createLocalButton(confirmLabel, confirmCallback);

        String editLabel = COMMENT_EDIT.localLabel(localMessageSource);
        String editCallback = COMMENT_EDIT.name();
        var editButton = KeyboardUtils.createLocalButton(editLabel, editCallback);

        List<InlineKeyboardButton> row = List.of(confirmButton, editButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        InlineKeyboardMarkup commentKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(commentKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
