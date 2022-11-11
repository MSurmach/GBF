package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.AllFeedbacksBotButton;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

@Service
public class AllFeedbacksHandlerType implements HandlerType {
    @Override
    public State getState() {
        return State.ALL_FEEDBACKS;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        AllFeedbacksBotButton clickedButton = AllFeedbacksBotButton.valueOf(callback);
        return clickedButton.getNextState();
    }
}
