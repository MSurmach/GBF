package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.service.message.Answer;
import org.springframework.stereotype.Service;

@Service
public class StateAnswerFactory implements Factory<Answer> {
    @Override
    public Answer get(State state) {
        return null;
    }
}
