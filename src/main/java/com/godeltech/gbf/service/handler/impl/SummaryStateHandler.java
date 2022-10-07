package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.COURIER;
import static com.godeltech.gbf.model.State.FOUND_COURIERS_INFO;
import static com.godeltech.gbf.model.State.SUCCESS;

@Service
@AllArgsConstructor
public class SummaryStateHandler implements StateHandler {

    private UserService userService;

    @Override
    public State handle(UserData userData) {
        Role role = userData.getRole();
        return switch (role) {
            case COURIER -> {
                userService.save(userData);
                yield SUCCESS;
            }
            case CLIENT -> {
                userService.save(userData);
                Page<UserRecord> recordsPage = userService.findCourierByUserDataAndRole(userData, COURIER, userData.getPageNumber());
                userData.setRecordsPage(recordsPage);
                yield FOUND_COURIERS_INFO;
            }
            default -> userData.getStateHistory().peek();
        };
    }
}