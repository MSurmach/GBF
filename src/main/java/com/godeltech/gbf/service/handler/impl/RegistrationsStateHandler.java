package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.button.RegistrationBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.model.State.REGISTRATION_EDITOR;

@Service
@AllArgsConstructor
public class RegistrationsStateHandler implements StateHandler {
    private UserDataService userDataService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        long telegramUserId = userData.getTelegramUserId();
        String[] splittedCallback = callback.split(":");
        var clickedButton = RegistrationBotButton.valueOf(splittedCallback[0]);
        Long recordId = Long.parseLong(splittedCallback[1]);
        switch (clickedButton) {
            case REGISTRATION_EDIT -> {
                UserData persisted = userDataService.findUserDataByTelegramUserIdAndId(telegramUserId, recordId);
                persisted.getStateHistory().push(REGISTRATION_EDITOR);
                UserDataCache.add(telegramUserId, persisted);
            }
            case REGISTRATION_DELETE -> {
                List<UserData> registrations = userData.getRegistrations();
                registrations.removeIf(registration -> Objects.equals(registration.getId(), recordId));
                userDataService.deleteById(recordId);
            }
        }
        return userData.getStateHistory().peek();
    }

}
