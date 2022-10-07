package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.button.RequestButton;
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
        long recordId = Long.parseLong(splitCallback[1]);
        long telegramUserId = userData.getTelegramUserId();
        return switch (clickedButton) {
            case REQUEST_EDIT -> {
                UserRecord requestRecord = userData.getRecordsPage().get().
                        filter(record -> record.getTelegramUserId() == telegramUserId && record.getRecordId() == recordId).
                        findFirst().
                        get();
                UserData dataFromRecord = new UserData(requestRecord);
                UserDataCache.add(telegramUserId, dataFromRecord);
                yield REQUEST_EDITOR;
            }
            case REQUEST_DELETE -> {
                userService.deleteById(recordId);
                userData.setPageNumber(0);
                yield userData.getStateHistory().peek();
            }
            case REQUEST_FIND_COURIERS -> {
                userData.setPageNumber(0);
                UserRecord requestRecord = userData.getRecordsPage().get().
                        filter(record -> record.getTelegramUserId() == telegramUserId && record.getRecordId() == recordId).
                        findFirst().
                        get();
                UserData requestData = new UserData(requestRecord);
                Page<UserRecord> recordsPage = userService.findByUserDataAndRole(requestData, COURIER, userData.getPageNumber());
                userData.setRecordsPage(recordsPage);
                yield FOUND_COURIERS_INFO;
            }
        };
    }
}
