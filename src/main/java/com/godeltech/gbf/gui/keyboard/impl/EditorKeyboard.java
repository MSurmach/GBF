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

import static com.godeltech.gbf.gui.button.EditorBotButton.*;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class EditorKeyboard implements Keyboard {
    private LocalMessageSource lms;
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(createLocalButton(EDIT_COUNTRY_CITY_FROM, lms)));
        keyboard.add(List.of(createLocalButton(EDIT_COUNTRY_CITY_TO, lms)));
        keyboard.add(
                userData.getDateFrom() != null ?
                        List.of(createLocalButton(EDIT_DATE_FROM, lms),
                                createLocalButton(DELETE_DATE_FROM, lms)) :
                        List.of(createLocalButton(ADD_DATE_FROM,lms)));
        keyboard.add(
                userData.getDateTo() != null ?
                        List.of(createLocalButton(EDIT_DATE_TO, lms),
                                createLocalButton(DELETE_DATE_TO, lms)) :
                        List.of(createLocalButton(ADD_DATE_TO, lms)));
        keyboard.add(List.of(createLocalButton(EDIT_CARGO, lms)));
        keyboard.add(
                userData.getComment() != null ?
                        List.of(createLocalButton(EDIT_COMMENT, lms),
                                createLocalButton(DELETE_COMMENT, lms)) :
                        List.of(createLocalButton(ADD_COMMENT, lms)));
        keyboard.add(List.of(createLocalButton(EDIT_CONFIRM, lms)));
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(controlKeyboard.getKeyboardMarkup(userData)).
                result();
    }
}
