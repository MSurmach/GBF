package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.RequestButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.FOUND_COURIERS_INFO;
import static com.godeltech.gbf.model.State.REQUEST_EDITOR;

@Service
@AllArgsConstructor
public class RequestsStateHandler implements StateHandler {

    private UserService userService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splitCallback = callback.split(":");
        RequestButton clickedButton = RequestButton.valueOf(splitCallback[0]);
        return switch (clickedButton) {
            case REQUEST_EDIT -> {
                yield REQUEST_EDITOR;
            }
            case REQUEST_DELETE -> {
                Long recordId = Long.parseLong(splitCallback[1]);
                userService.deleteById(recordId);
                userData.setPageNumber(0);
                yield userData.getStateHistory().peek();
            }
            case REQUEST_FIND_COURIERS -> {
                yield FOUND_COURIERS_INFO;
            }
        };
    }
}
