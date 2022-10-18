package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CargoBotButton.*;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class CargoMainKeyboard implements Keyboard {

    private ControlKeyboard controlKeyboard;
    private LocalMessageSource lms;

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
        var confirmButton = KeyboardUtils.createLocalButton(CONFIRM_CARGO, lms);
        return new ArrayList<>(List.of(confirmButton));
    }

    private List<InlineKeyboardButton> documentsRow(UserData userData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton documentButton;
        if (userData.isDocumentsExist()) {
            documentButton = KeyboardUtils.createLocalButton(CANCEL_DOCUMENTS, lms);
        } else {
            documentButton = KeyboardUtils.createLocalButton(SELECT_DOCUMENTS, lms);
        }
        row.add(documentButton);
        return row;
    }

    private List<InlineKeyboardButton> packageRow(UserData userData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        if (userData.getPackageSize() != 0) {
            var cancelButton = KeyboardUtils.createLocalButton(CANCEL_PACKAGE, lms);
            var editButton = KeyboardUtils.createLocalButton(EDIT_PACKAGE, lms);
            row.add(cancelButton);
            row.add(editButton);
        } else {
            var selectPackage = KeyboardUtils.createLocalButton(SELECT_PACKAGE, lms);
            row.add(selectPackage);
        }
        return row;
    }

    private List<InlineKeyboardButton> companionRow(UserData userData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        if (userData.getCompanionCount() != 0) {
            var cancelButton = KeyboardUtils.createLocalButton(CANCEL_PEOPLE, lms);
            var editButton = KeyboardUtils.createLocalButton(EDIT_PEOPLE, lms);
            row.add(cancelButton);
            row.add(editButton);
        } else {
            var selectPackage = KeyboardUtils.createLocalButton(SELECT_PEOPLE, lms);
            row.add(selectPackage);
        }
        return row;
    }
}
