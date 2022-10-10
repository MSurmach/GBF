package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;

public interface Handler {
    State handle(UserData userData);
}
