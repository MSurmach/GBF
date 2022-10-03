package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindCourierStateHandler implements StateHandler {
    private UserDataRepository userDataRepository;

    @Override
    public State handle(UserData userData) {
        return null;
    }
}
