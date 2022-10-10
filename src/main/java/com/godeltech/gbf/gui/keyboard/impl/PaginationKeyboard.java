package com.godeltech.gbf.gui.keyboard.impl;

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

import static com.godeltech.gbf.gui.button.PaginationButton.*;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class PaginationKeyboard implements Keyboard {
    private BackMenuKeyboard backMenuKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var startButton = KeyboardUtils.createLocalButton(START.label, START.name());
        var prevButton = KeyboardUtils.createLocalButton(PREVIOUS.label, PREVIOUS.name());
        var pageNumber = userData.getPageNumber() + 1;
        var pageButton = KeyboardUtils.createLocalButton(PAGE.label.formatted(pageNumber), PAGE.name());
        var nextButton = KeyboardUtils.createLocalButton(NEXT.label, NEXT.name());
        var endButton = KeyboardUtils.createLocalButton(END.label, END.name());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(startButton, prevButton, pageButton, nextButton, endButton));
        var paginationKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender().
                append(backMenuKeyboard.getKeyboardMarkup(userData)).
                append(paginationKeyboardMarkup).
                result();
    }
}
