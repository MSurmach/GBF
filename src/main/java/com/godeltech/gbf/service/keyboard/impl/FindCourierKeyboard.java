package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.BotButton.FindCourier.LOOK_AT_COURIERS;

@Service
@AllArgsConstructor
public class FindCourierKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<UserData> foundUsers = userData.getFoundUsers();
        if (!foundUsers.isEmpty()) return controlKeyboard.getKeyboardMarkup(userData);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        var lookButton = InlineKeyboardButton.
                builder().
                text(LOOK_AT_COURIERS.getLocalMessage(localMessageSource)).
                callbackData("").
                build();
        keyboard.add(List.of(lookButton));
        var keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
