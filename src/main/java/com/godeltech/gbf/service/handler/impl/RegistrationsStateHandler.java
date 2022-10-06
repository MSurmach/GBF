package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.button.RegistrationBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.godeltech.gbf.model.State.REGISTRATION_EDITOR;

@Service
@AllArgsConstructor
public class RegistrationsStateHandler implements StateHandler {
    private UserService userService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        long telegramUserId = userData.getTelegramUserId();
        String[] splittedCallback = callback.split(":");
        var clickedButton = RegistrationBotButton.valueOf(splittedCallback[0]);
        Long recordId = Long.parseLong(splittedCallback[1]);
        return switch (clickedButton) {
            case REGISTRATION_EDIT -> {
                UserRecord record = userService.findByTelegramUserIdAndRecordId(telegramUserId, recordId);
                UserData dataFromRecord = new UserData(record);
                UserDataCache.add(telegramUserId, dataFromRecord);
                yield REGISTRATION_EDITOR;
            }
            case REGISTRATION_DELETE -> {
                userService.deleteById(recordId);
                yield userData.getStateHistory().peek();
            }
        };
    }

}
