package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.button.EditorBotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.EditorBotButton.*;

@Component
@AllArgsConstructor
public class EditorKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<List<InlineKeyboardButton>> editKeyboard = new ArrayList<>();
        editKeyboard.add(List.of(buildButton(EDIT_COUNTRY_CITY_FROM)));
        editKeyboard.add(List.of(buildButton(EDIT_COUNTRY_CITY_TO)));
        editKeyboard.add(
                userData.getDateFrom() != null ?
                        List.of(buildButton(EDIT_DATE_FROM),
                                buildButton(DELETE_DATE_FROM)) :
                        List.of(buildButton(ADD_DATE_FROM)));
        editKeyboard.add(
                userData.getDateTo() != null ?
                        List.of(buildButton(EDIT_DATE_TO),
                                buildButton(DELETE_DATE_TO)) :
                        List.of(buildButton(ADD_DATE_TO)));
        editKeyboard.add(List.of(buildButton(EDIT_CARGO)));
        editKeyboard.add(
                userData.getComment() != null ?
                        List.of(buildButton(EDIT_COMMENT),
                                buildButton(DELETE_COMMENT)) :
                        List.of(buildButton(ADD_COMMENT)));
        editKeyboard.add(List.of(buildButton(EDIT_CONFIRM)));
        var editMarkup = new InlineKeyboardMarkup(editKeyboard);
        return new KeyboardMarkupAppender().
                append(editMarkup).
                append(controlKeyboard.getKeyboardMarkup(userData)).
                result();
    }

    private InlineKeyboardButton buildButton(EditorBotButton button) {
        String label = button.localLabel(localMessageSource);
        String callback = button.name();
        return KeyboardUtils.createLocalButton(label, callback);
    }
}
