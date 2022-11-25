package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Session;

public interface HandlerType {
    State getState();
    State handle(Session session);
}
