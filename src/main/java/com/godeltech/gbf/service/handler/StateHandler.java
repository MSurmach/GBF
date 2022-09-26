package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.model.UserData;

public interface StateHandler {
    void handle(Long userId, UserData userData);

}
