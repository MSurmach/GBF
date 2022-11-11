package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.feedback.FeedbackService;
import com.godeltech.gbf.service.handler.HandlerType;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.ALL_FEEDBACKS;

@Service
@AllArgsConstructor
public class FeedbackDeleteByIdHandlerType implements HandlerType {
    private final FeedbackService feedbackService;

    @Override
    public State getState() {
        return State.FEEDBACK_DELETE_BY_ID;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        try {
            Long feedBackId = Long.parseLong(callback);
            feedbackService.deleteFeedbackById(feedBackId);
            return ALL_FEEDBACKS;
        } catch (NumberFormatException numberFormatException) {
            return sessionData.getStateHistory().pop();
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return ALL_FEEDBACKS;
        }
    }
}
