package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.godeltech.gbf.model.State.FOUND_COURIERS_INFO;
import static com.godeltech.gbf.model.State.SUCCESS;

@Service
@AllArgsConstructor
public class SummaryStateHandler implements StateHandler {

    private UserDataService userDataService;

    @Override
    public State handle(UserData userData) {
        Role role = userData.getRole();
        return switch (role) {
            case COURIER -> {
                userDataService.save(userData);
                yield SUCCESS;
            }
            case CUSTOMER -> {
                List<UserData> foundUsers = userDataService.findByUserData(userData);
                userData.setFoundCouriers(foundUsers);
                yield FOUND_COURIERS_INFO;
            }
            default -> userData.getStateHistory().peek();
        };
    }
}
