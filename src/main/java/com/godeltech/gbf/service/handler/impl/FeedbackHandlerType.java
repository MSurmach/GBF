package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Feedback;
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
    public State handle(Session session) {
        String feedBackContent = session.getCallbackHistory().peek();
        session.getStateHistory().pop();
        feedbackService.save(Feedback.builder().
                content(feedBackContent).
                userId(session.getTelegramUser().getId()).
                build());
        return THANKS_FOR_FEEDBACK;
    }
}
