package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.WRONG_INPUT_CODE;

@Component
@AllArgsConstructor
@Slf4j
public class WrongInputTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.WRONG_INPUT;
    }

    @Override
    public String getMessage(Session session) {
        log.debug("Create wrong input message type for session data with user: {}",
                session.getTelegramUser());
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        return lms.getLocaleMessage(WRONG_INPUT_CODE, session.getTelegramUser().getUserName());
    }
}
