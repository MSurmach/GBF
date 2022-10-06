package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.PaginationButton.*;
import static com.godeltech.gbf.utils.KeyboardUtils.createButton;

@Service
@AllArgsConstructor
public class PaginationKeyboard implements Keyboard {
    private BackMenuKeyboard backMenuKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var startButton = createButton(START.label, START.name());
        var prevButton = createButton(PREVIOUS.label, PREVIOUS.name());
        var pageNumber = userData.getPageNumber() + 1;
        var pageButton = createButton(PAGE.label.formatted(pageNumber), PAGE.name());
        var nextButton = createButton(NEXT.label, NEXT.name());
        var endButton = createButton(END.label, END.name());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(startButton, prevButton, pageButton, nextButton, endButton));
        var paginationKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender().
                append(backMenuKeyboard.getKeyboardMarkup(userData)).
                append(paginationKeyboardMarkup).
                result();
    }
}
