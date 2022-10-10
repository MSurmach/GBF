package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.button.RequestButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.RequestButton.*;

@Component
@AllArgsConstructor
public class RequestKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        Long recordId = userData.getRecordId();
        var editButton = buildButton(REQUEST_EDIT, recordId);
        var deleteButton = buildButton(REQUEST_DELETE, recordId);
        var findButton = buildButton(REQUEST_FIND_COURIERS, recordId);
        List<List<InlineKeyboardButton>> requestKeyboard = new ArrayList<>();
        requestKeyboard.add(List.of(editButton, deleteButton, findButton));
        return new InlineKeyboardMarkup(requestKeyboard);
    }

    private InlineKeyboardButton buildButton(RequestButton button, Long recordId) {
        String label = button.localLabel(localMessageSource);
        String callback = button.name() + ":" + recordId;
        return KeyboardUtils.createLocalButton(label, callback);
    }
}
