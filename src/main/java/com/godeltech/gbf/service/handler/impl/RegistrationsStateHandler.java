package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.godeltech.gbf.management.State.REGISTRATIONS;
import static com.godeltech.gbf.management.State.REGISTRATION_EDITOR;

@Service
@AllArgsConstructor
public class RegistrationsStateHandler implements StateHandler {
    private UserDataRepository userDataRepository;

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        Long telegramUserId = userData.getTelegramUserId();
        if (callback.equals(REGISTRATIONS.name())) {
            List<UserData> usersByTelegramId = userDataRepository.findUserDataByTelegramUserId(telegramUserId);
            userData.setRegistrations(usersByTelegramId);
            userData.setCurrentState(REGISTRATIONS);
        } else {
            String[] splittedCallback = callback.split(":");
            BotButton.Registration clickedButton = BotButton.Registration.valueOf(splittedCallback[0]);
            Long recordId = Long.parseLong(splittedCallback[1]);
            switch (clickedButton) {
                case REGISTRATION_EDIT -> {
                    UserData persisted = userDataRepository.findUserDataByTelegramUserIdAndId(telegramUserId, recordId);
                    persisted.setCurrentState(REGISTRATION_EDITOR);
                    UserDataCache.add(telegramUserId, persisted);
                }
                case REGISTRATION_DELETE -> {
                    List<UserData> registrations = userData.getRegistrations();
                    registrations.removeIf(registration -> registration.getId() == recordId);
                    userDataRepository.deleteById(recordId);
                }
            }
        }
    }
}
