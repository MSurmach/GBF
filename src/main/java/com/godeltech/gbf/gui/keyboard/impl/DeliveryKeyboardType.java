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
import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ButtonUtils.createButton;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.ConstantUtil.DELIVERY_MARKER_CODE;
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
                sessionData.getTelegramUserId(), sessionData.getUsername());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        Delivery[] deliveries = Delivery.values();
        Delivery selectedDelivery = sessionData.getDelivery();
        for (var index = 0; index < deliveries.length; ) {
            var columnCount = 3;
            List<InlineKeyboardButton> deliveryButtonRow = new ArrayList<>();
            while (columnCount > 0 && index != deliveries.length) {
                Delivery delivery = deliveries[index];
                deliveryButtonRow.add(
                        !Objects.isNull(selectedDelivery) && Objects.equals(delivery, selectedDelivery) ?
                                createButton(attachMarkToLabel(delivery.name()), delivery.name()) :
                                createLocalButton(delivery.name(), delivery.name(), lms));
                columnCount--;
                index++;
            }
            keyboard.add(deliveryButtonRow);
        }
        return new KeyboardMarkupAppender(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private String attachMarkToLabel(String label) {
        return lms.getLocaleMessage(label) + lms.getLocaleMessage(DELIVERY_MARKER_CODE);
    }
}
