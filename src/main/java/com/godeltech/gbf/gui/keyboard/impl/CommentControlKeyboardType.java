package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import org.springframework.stereotype.Component;

@Component
public class CommentControlKeyboardType  extends ControlKeyboardType implements KeyboardType {
    public CommentControlKeyboardType(LocalMessageSource lms) {
        super(lms);
    }

    @Override
    public State getState() {
        return State.COMMENT;
    }
}

