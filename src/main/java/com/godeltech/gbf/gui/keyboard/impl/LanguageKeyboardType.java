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
import java.util.stream.Stream;

import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.menuMarkup;

@Component
@AllArgsConstructor
public class LanguageKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.LANGUAGE;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        List<InlineKeyboardButton> buttons = Stream.of(EN_CODE, RU_CODE, BE_CODE).map(s -> createLocalButton(s, s, lms)).toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttons);
        return new KeyboardMarkupAppender(new InlineKeyboardMarkup(keyboard)).
                append(menuMarkup(lms)).
                result();
    }
}
