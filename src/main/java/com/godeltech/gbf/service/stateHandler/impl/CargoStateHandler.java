package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.model.Cargo;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class CargoStateHandler implements BotStateHandler {
    @Override
    public SendMessage handleUpdate(Update update) {
        return null;
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<Cargo> cargos = List.of(Cargo.values());
        List<InlineKeyboardButton> buttons = cargos.stream().
                map(cargo -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(cargo.name());
                    button.setCallbackData(cargo.name());
                    return button;
                }).toList();
        return new InlineKeyboardMarkup(List.of(buttons));
    }
}
