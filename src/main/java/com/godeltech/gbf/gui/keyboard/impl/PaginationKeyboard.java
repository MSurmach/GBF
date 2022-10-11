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

import static com.godeltech.gbf.gui.button.PaginationButton.*;
import static com.godeltech.gbf.utils.KeyboardUtils.createButton;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class PaginationKeyboard implements Keyboard {
    private BackMenuKeyboard backMenuKeyboard;
    private LocalMessageSource lms;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var startButton = createLocalButton(PAGE_START, lms);
        var prevButton = createLocalButton(PAGE_PREVIOUS, lms);
        var pageNumber = userData.getPageNumber() + 1;
        var pageButton = createButton(
                lms.getLocaleMessage(PAGE_CURRENT.name(), String.valueOf(pageNumber)),
                PAGE_CURRENT.name());
        var nextButton = createLocalButton(PAGE_NEXT, lms);
        var endButton = createLocalButton(PAGE_END, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(startButton, prevButton, pageButton, nextButton, endButton));
        var paginationKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender().
                append(backMenuKeyboard.getKeyboardMarkup(userData)).
                append(paginationKeyboardMarkup).
                result();
    }
}
