package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.impl.NotFoundKeyboardType;
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
public class KeyboardFactory implements Factory<KeyboardType> {


    private final Map<State, KeyboardType> keyboardContext;

    @Autowired
    public KeyboardFactory(List<KeyboardType> keyboardTypeContext) {
        this.keyboardContext = keyboardTypeContext.stream().
                collect(Collectors.toMap(KeyboardType::getState, Function.identity()));
    }

    @Override
    public KeyboardType get(State state) {
        log.info("Get keyboard type by state : {}",state);
        return keyboardContext.getOrDefault(state,
                (KeyboardType) new NotFoundKeyboardType());
    }
}
