package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.AdminInstrumentsBotButton;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminInstrumentsHandlerType implements HandlerType {
    @Override
    public State getState() {
        return State.ADMIN_INSTRUMENTS;
    }

    @Override
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        var clickedButton = AdminInstrumentsBotButton.valueOf(callback);
        return clickedButton.getNextState();
    }
}
