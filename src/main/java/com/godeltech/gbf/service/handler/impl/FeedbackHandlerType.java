package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Feedback;
import com.godeltech.gbf.service.feedback.FeedbackService;
import com.godeltech.gbf.service.handler.HandlerType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.FEEDBACK;
import static com.godeltech.gbf.model.State.MENU;

@Service
@AllArgsConstructor
public class FeedbackHandlerType implements HandlerType {
    private final FeedbackService feedbackService;

    @Override
    public State getState() {
        return FEEDBACK;
    }

    @Override
    public State handle(SessionData sessionData) {
        String feedBackContent = sessionData.getCallbackHistory().peek();
        sessionData.getStateHistory().pop();
        feedbackService.save(Feedback.builder().
                content(feedBackContent).
                userId(sessionData.getTelegramUserId()).
                build());
        return MENU;
    }
}
