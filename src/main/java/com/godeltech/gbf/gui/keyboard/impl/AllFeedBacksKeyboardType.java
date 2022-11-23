package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.AllFeedbacksBotButton.DELETE_BY_ID;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
public class AllFeedBacksKeyboardType implements KeyboardType {

    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.ALL_FEEDBACKS;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        var deleteByIdButton = createLocalButton(DELETE_BY_ID, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (!session.getFeedbacks().isEmpty() || session.getFeedbacks() == null)
            keyboard.add(List.of(deleteByIdButton));
        return new KeyboardMarkupAppender(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }
}
