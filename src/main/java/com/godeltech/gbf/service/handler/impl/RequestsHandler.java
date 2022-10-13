package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RequestButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.COURIERS_LIST;
import static com.godeltech.gbf.model.State.REQUEST_EDITOR;

@Service
@AllArgsConstructor
public class RequestsHandler implements Handler {

    private UserService userService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splitCallback = callback.split(":");
        RequestButton clickedButton = RequestButton.valueOf(splitCallback[0]);
        long recordId = Long.parseLong(splitCallback[1]);
        userData.setPageNumber(0);
        return switch (clickedButton) {
            case REQUEST_EDIT -> {
                UserRecord record = getRecordFromPage(userData, recordId);
               // userData.copyDataRecord(record);
                yield REQUEST_EDITOR;
            }
            case REQUEST_DELETE -> {
                userService.deleteById(recordId);
                yield userData.getStateHistory().peek();
            }
            case REQUEST_FIND_COURIERS -> {
                UserRecord record = getRecordFromPage(userData, recordId);
               // userData.setTempForSearch(new UserData(record));
                yield COURIERS_LIST;
            }
        };
    }

    private UserRecord getRecordFromPage(UserData userData, long recordId) {
        long telegramUserId = userData.getTelegramId();
        return userData.getRecordsPage().get().
                filter(record -> record.getTelegramUserId() == telegramUserId && record.getRecordId() == recordId).
                findFirst().
                get();
    }
}
