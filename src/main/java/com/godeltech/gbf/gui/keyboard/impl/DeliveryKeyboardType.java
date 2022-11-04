package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Delivery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@Slf4j
@AllArgsConstructor
public class DeliveryKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DELIVERY;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        log.debug("Create delivery keyboard type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        Delivery[] deliveries = Delivery.values();
        for (var index = 1; index < deliveries.length; ) {
            var columnCount = 3;
            List<InlineKeyboardButton> deliveryButtonRow = new ArrayList<>();
            while (columnCount > 0 && index != deliveries.length) {
                deliveryButtonRow.add(createLocalButton(deliveries[index].name(), deliveries[index].name(), lms));
                columnCount--;
                index++;
            }
            keyboard.add(deliveryButtonRow);
        }
        keyboard.add(List.of(createLocalButton(Delivery.EMPTY.name(), Delivery.EMPTY.name(), lms)));
        return new KeyboardMarkupAppender(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }
}
