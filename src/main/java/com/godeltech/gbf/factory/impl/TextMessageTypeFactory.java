package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.gui.text_message.impl.NotFoundTextMessageType;
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
public class TextMessageTypeFactory implements Factory<TextMessageType, State> {
    private final Map<State, TextMessageType> messageContext;

    @Autowired
    public TextMessageTypeFactory(List<TextMessageType> textMessageTypes) {
        this.messageContext = textMessageTypes.stream()
                .collect(Collectors.toMap(TextMessageType::getState, Function.identity()));
    }

    @Override
    public TextMessageType get(State state) {
        log.info("Get message type by state : {}",state);
        return messageContext.getOrDefault(state, (TextMessageType) new NotFoundTextMessageType());
    }
}
