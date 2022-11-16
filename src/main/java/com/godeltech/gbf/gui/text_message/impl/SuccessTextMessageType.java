package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.SUCCESS_CODE;

@Component
@AllArgsConstructor
@Slf4j
public class SuccessTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.SUCCESS_REGISTRATION;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create success message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
        return lms.getLocaleMessage(SUCCESS_CODE);
    }
}
