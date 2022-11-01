package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;

public interface HandlerType {
    State getState();
    State handle(SessionData sessionData);
}
