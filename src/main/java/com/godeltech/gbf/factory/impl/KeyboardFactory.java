package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.impl.EmptyKeyboardType;
import com.godeltech.gbf.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class KeyboardFactory implements Factory<KeyboardType> {


    private final Map<State, KeyboardType> keyboardContext;

    @Autowired
    public KeyboardFactory(List<KeyboardType> keyboardTypeContext) {
        this.keyboardContext = keyboardTypeContext.stream().
                collect(Collectors.toMap(KeyboardType::getState, Function.identity()));
    }

    @Override
    public KeyboardType get(State state) {
        return keyboardContext.getOrDefault(state,
                (KeyboardType) new EmptyKeyboardType());
    }
}
