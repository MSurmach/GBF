package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CommentBotButton.COMMENT_CONFIRM;
import static com.godeltech.gbf.gui.button.CommentBotButton.COMMENT_EDIT;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class CommentConfirmationKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource lms;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var confirmButton = createLocalButton(COMMENT_CONFIRM, lms);
        var editButton = createLocalButton(COMMENT_EDIT, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(confirmButton, editButton));
        return new KeyboardMarkupAppender(new InlineKeyboardMarkup(keyboard)).
                append(controlKeyboard.getKeyboardMarkup(userData)).
                result();
    }
}
