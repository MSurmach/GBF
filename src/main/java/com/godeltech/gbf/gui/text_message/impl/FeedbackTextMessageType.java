package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_ABOUT_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_QUESTION_CODE;

@Component
@AllArgsConstructor
public class FeedbackTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.FEEDBACK;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        return lms.getLocaleMessage(FEEDBACK_ABOUT_CODE) + lms.getLocaleMessage(FEEDBACK_QUESTION_CODE);
    }
}
