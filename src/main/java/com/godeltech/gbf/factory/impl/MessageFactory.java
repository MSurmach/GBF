package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.impl.NotFoundMessageType;
import com.godeltech.gbf.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessageFactory implements Factory<MessageType> {
    private final Map<State, MessageType> messageContext;

    @Autowired
    public MessageFactory(List<MessageType> messageTypes) {
        this.messageContext = messageTypes.stream()
                .collect(Collectors.toMap(MessageType::getState, Function.identity()));
    }

    @Override
    public MessageType get(State state) {
        log.info("Get message type by state : {}",state);
        return messageContext.getOrDefault(state, (MessageType) new NotFoundMessageType());
    }
}
