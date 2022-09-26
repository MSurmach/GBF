package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.BotButton;
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

import static com.godeltech.gbf.management.button.BotButton.Cargo.*;

@Service
@AllArgsConstructor
public class CargoMenuKeyboard implements Keyboard {

    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(documentsRow(userData));
        keyboard.add(packageRow(userData));
        keyboard.add(companionRow(userData));
        keyboard.add(confirmCargoRow());
        InlineKeyboardMarkup loadKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(loadKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }

    private List<InlineKeyboardButton> confirmCargoRow() {
        var confirmButton = buildCommandButton(CONFIRM_CARGO);
        return new ArrayList<>(List.of(confirmButton));
    }

    private List<InlineKeyboardButton> documentsRow(UserData userData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton documentButton;
        if (userData.isDocuments()) {
            documentButton = buildCommandButton(CANCEL_DOCUMENTS);
        } else {
            documentButton = buildCommandButton(SELECT_DOCUMENTS);
        }
        row.add(documentButton);
        return row;
    }

    private List<InlineKeyboardButton> packageRow(UserData userData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        if (userData.getPackageSize() != null) {
            var cancelButton = buildCommandButton(CANCEL_PACKAGE);
            var editButton = buildCommandButton(EDIT_PACKAGE);
            row.add(cancelButton);
            row.add(editButton);
        } else {
            var selectPackage = buildCommandButton(SELECT_PACKAGE);
            row.add(selectPackage);
        }
        return row;
    }

    private List<InlineKeyboardButton> companionRow(UserData userData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        if (userData.getCompanionCount() != 0) {
            var cancelButton = buildCommandButton(CANCEL_PEOPLE);
            var editButton = buildCommandButton(EDIT_PEOPLE);
            row.add(cancelButton);
            row.add(editButton);
        } else {
            var selectPackage = buildCommandButton(SELECT_PEOPLE);
            row.add(selectPackage);
        }
        return row;
    }

    private InlineKeyboardButton buildCommandButton(BotButton.Cargo command) {
        String label = command.getLocalMessage(localMessageSource);
        String callback = command.name();
        return KeyboardUtils.createButton(label, callback);
    }
}
