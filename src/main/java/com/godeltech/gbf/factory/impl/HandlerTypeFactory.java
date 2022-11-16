package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.handler.impl.NotFoundHandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HandlerTypeFactory implements Factory<HandlerType, State> {

    private final Map<State, HandlerType> handlerContext;

    public HandlerTypeFactory(List<HandlerType> handlerTypes) {
        this.handlerContext = handlerTypes.stream()
                .collect(Collectors.toMap(HandlerType::getState, Function.identity()));
    }

    @Override
    public HandlerType get(State state) {
        log.info("Get handler type by state : {}",state);
            return handlerContext.getOrDefault(state,(HandlerType) new NotFoundHandlerType());
    }
}
