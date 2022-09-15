package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.ConfirmKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ConfirmStateHandler extends LocaleBotStateHandler {
    private Keyboard keyboard;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setKeyboard(ConfirmKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public ConfirmStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public void handleUpdate(Update update) {
        UserData cachedUserData = UserDataCache.get(update.getCallbackQuery().getFrom().getId());
        BotStateFlow botStateFlow = cachedUserData.getBotStateFlow();
        if (botStateFlow == BotStateFlow.COURIER) {
            User user = new User(cachedUserData);
            userRepository.save(user);
        }
        cachedUserData.setBotState(botStateFlow.getNextState(cachedUserData.getBotState()));
    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        UserData cachedUserData = UserDataCache.get(update.getCallbackQuery().getFrom().getId());
        sendMessage.setText(textAnswer(cachedUserData));
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotStateFlow botStateFlow = userData.getBotStateFlow();
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        switch (botStateFlow) {
            case COURIER -> {
                stringBuilder.append(localeMessageSource.getLocaleMessage("confirm.courier", username));
                stringBuilder.append("\n\n");
                String confirmationData = localeMessageSource.getLocaleMessage("courier.user.data",
                        userData.getCountryFrom(),
                        userData.getCityFrom(),
                        userData.getDayFrom(),
                        userData.getMonthFrom(),
                        userData.getYearFrom(),
                        userData.getCountryTo(),
                        userData.getCityTo(),
                        userData.getDayTo(),
                        userData.getMonthTo(),
                        userData.getYearTo(),
                        userData.getLoad().name());
                stringBuilder.append(confirmationData);
            }
            case CUSTOMER -> {
                stringBuilder.append(localeMessageSource.getLocaleMessage("confirm.customer", username));
                stringBuilder.append("\n\n");
                String confirmationData = localeMessageSource.getLocaleMessage("customer.user.data",
                        userData.getCountryFrom(),
                        userData.getCityFrom(),
                        userData.getCountryTo(),
                        userData.getCityTo(),
                        userData.getLoad().name());
                stringBuilder.append(confirmationData);
            }
        }
        return stringBuilder.toString();
    }
}
