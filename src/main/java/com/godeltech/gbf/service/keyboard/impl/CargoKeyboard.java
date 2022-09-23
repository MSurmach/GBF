package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.controls.Command.Cargo.*;
import static com.godeltech.gbf.model.Cargo.DOCUMENTS;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class CargoKeyboard extends LocaleKeyboard {

    private Keyboard controlKeyboard;

    public CargoKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(documentsRow(callback));
        keyboard.add(confirmCargoRow());
        InlineKeyboardMarkup loadKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(loadKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }

    private List<InlineKeyboardButton> confirmCargoRow() {
        String label = localMessageSource.getLocaleMessage("CONFIRM");
        String buttonCallback = CONFIRM_CARGO.name();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(createButton(label, buttonCallback));
        return row;
    }

    private List<InlineKeyboardButton> documentsRow(Long userId) {
        UserData userData = UserDataCache.get(userId);
        List<InlineKeyboardButton> row = new ArrayList<>();
        String label;
        String buttonCallback;
        if (userData.getDocuments()) {
            label = localMessageSource.getLocaleMessage("CANCEL_DOCUMENTS");
            buttonCallback = CANCEL_DOCUMENTS.name();
        } else {
            label = DOCUMENTS.getLocalDescription(localMessageSource);
            buttonCallback = SELECT_DOCUMENTS.name();
        }
        row.add(createButton(label, buttonCallback));
        return row;
    }

    private List<InlineKeyboardButton> packageRow(Long userId) {
        UserData userData = UserDataCache.get(userId);
        List<InlineKeyboardButton> row = new ArrayList<>();
        if (userData.getPackageSize() != null) {
            var cancelLabel = localMessageSource.getLocaleMessage("CANCEL_PACKAGE");
            var cancelCallback = CANCEL_PACKAGE.name();
            var cancelButton = createButton(cancelLabel, cancelCallback);

            var editLabel = localMessageSource.getLocaleMessage("EDIT_PACKAGE");
            var editCallBack = EDIT_PACKAGE.name();
            var editButton = createButton(editLabel, editCallBack);

            row.add(cancelButton);
            row.add(editButton);
        } else {
            var packageLabel =
        }
        row.add(createButton(cancelLabel, cancelCallback));
        return row;
    }
}
