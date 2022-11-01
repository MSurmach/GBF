package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.godeltech.gbf.model.State.*;

@Service
public class BackHandlerType implements HandlerType {
    @Override
    public State getState() {
        return BACK;
    }

    public final static Map<State, State> stateAndBackStateMap = new HashMap<>();

    static {
        stateAndBackStateMap.put(FORM, MENU);
        stateAndBackStateMap.put(REQUESTS, MENU);
        stateAndBackStateMap.put(REGISTRATIONS, MENU);
        stateAndBackStateMap.put(WRONG_INPUT, MENU);
        stateAndBackStateMap.put(COMMENT, FORM);
        stateAndBackStateMap.put(SEATS, FORM);
        stateAndBackStateMap.put(DELIVERY, FORM);
        stateAndBackStateMap.put(DATE, FORM);
        stateAndBackStateMap.put(ROUTE, FORM);
        stateAndBackStateMap.put(YEAR, DATE);
        stateAndBackStateMap.put(MONTH, DATE);
        stateAndBackStateMap.put(COURIERS_LIST_RESULT, REQUESTS);
        stateAndBackStateMap.put(CLIENTS_LIST_RESULT, REGISTRATIONS);
    }

    @Override
    public State handle(SessionData sessionData) {
        State currentState = sessionData.getStateHistory().pop();
        return stateAndBackStateMap.get(currentState);
    }
}
