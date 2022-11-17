package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Feedback;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.feedback.FeedbackService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.FEEDBACK;
import static com.godeltech.gbf.model.State.THANKS_FOR_FEEDBACK;

@Service
@AllArgsConstructor
public class FeedbackHandlerType implements HandlerType {
    private final FeedbackService feedbackService;
    private final TelegramUserService telegramUserService;

    @Override
    public State getState() {
        return FEEDBACK;
    }

    @Override
    public State handle(SessionData sessionData) {
        String feedBackContent = sessionData.getCallbackHistory().peek();
        sessionData.getStateHistory().pop();
        TelegramUser user = telegramUserService.saveUser(sessionData.getTelegramUserId(), sessionData.getUsername(), sessionData.getLanguage());
        feedbackService.save(Feedback.builder().
                content(feedBackContent).
                userId(user.getId()).
                build());
        return THANKS_FOR_FEEDBACK;
    }
}
