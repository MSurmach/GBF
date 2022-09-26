package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.service.keyboard.Keyboard;
import org.springframework.stereotype.Service;

@Service
public class StateKeyboardFactory implements Factory<Keyboard> {

    @Override
    public Keyboard get(State state) {
        return null;
    }
}
