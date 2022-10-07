package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.RegistrationBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.CLIENT;
import static com.godeltech.gbf.model.State.CLIENTS_LIST;
import static com.godeltech.gbf.model.State.REGISTRATION_EDITOR;

@Service
@AllArgsConstructor
public class RegistrationsStateHandler implements StateHandler {
    private UserService userService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clickedButton = RegistrationBotButton.valueOf(splittedCallback[0]);
        long recordId = Long.parseLong(splittedCallback[1]);
        userData.setPageNumber(0);
        return switch (clickedButton) {
            case REGISTRATION_EDIT -> {
                UserRecord record = getRecordFromPage(userData, recordId);
                userData.copyDataRecord(record);
                yield REGISTRATION_EDITOR;
            }
            case REGISTRATION_DELETE -> {
                userService.deleteById(recordId);
                yield userData.getStateHistory().peek();
            }
            case REGISTRATION_FIND_CLIENTS -> {
                UserRecord record = getRecordFromPage(userData, recordId);
                Page<UserRecord> recordsPage = userService.findClientByUserDataAndRole(new UserData(record), CLIENT, userData.getPageNumber());
                userData.setRecordsPage(recordsPage);
                yield CLIENTS_LIST;
            }
        };
    }

    private UserRecord getRecordFromPage(UserData userData, long recordId) {
        long telegramUserId = userData.getTelegramUserId();
        return userData.getRecordsPage().get().
                filter(record -> record.getTelegramUserId() == telegramUserId && record.getRecordId() == recordId).
                findFirst().
                get();
    }
}
