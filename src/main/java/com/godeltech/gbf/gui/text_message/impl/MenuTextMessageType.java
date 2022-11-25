package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.MENU_CODE;

@Component
@AllArgsConstructor
@Slf4j
public class MenuTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.MENU;
    }

    @Override
    public String getMessage(Session session) {
        log.debug("Create form message type for session data with user: {}",
                session.getTelegramUser());
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        return lms.getLocaleMessage(MENU_CODE, ModelUtils.getUserMention(session));
    }
}
