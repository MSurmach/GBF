package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.gui.button.MainMenuButton.*;

@Component
@AllArgsConstructor
public class MainMenuKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String courierLabel = COURIER.localLabel(localMessageSource);
        String courierCallback = COURIER.name();
        var courierButton = KeyboardUtils.createLocalButton(courierLabel, courierCallback);

        String clientLabel = CLIENT.localLabel(localMessageSource);
        String clientCallback = CLIENT.name();
        var clientButton = KeyboardUtils.createLocalButton(clientLabel, clientCallback);

        String registrationsLabel = REGISTRATIONS_VIEWER.localLabel(localMessageSource);
        String registrationsCallback = REGISTRATIONS_VIEWER.name();
        var registrationsButton = KeyboardUtils.createLocalButton(registrationsLabel, registrationsCallback);

        String requestsLabel = REQUESTS_VIEWER.localLabel(localMessageSource);
        String requestsCallback = REQUESTS_VIEWER.name();
        var requestsButton = KeyboardUtils.createLocalButton(requestsLabel, requestsCallback);

        List<List<InlineKeyboardButton>> keyboard = List.of(
                List.of(courierButton),
                List.of(clientButton),
                List.of(registrationsButton, requestsButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}
