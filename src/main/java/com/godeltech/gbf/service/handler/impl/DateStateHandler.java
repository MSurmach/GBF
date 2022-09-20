package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.CalendarCommand;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.impl.CalendarKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

import static com.godeltech.gbf.model.BotState.DATE_FROM;

@Service
public class DateStateHandler extends LocaleBotStateHandler {

    public DateStateHandler(LocaleMessageSource localeMessageSource, CalendarKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {
        String callback = update.getCallbackQuery().getData();
        Long userId = update.getCallbackQuery().getFrom().getId();
        UserData cachedUserData = UserDataCache.get(userId);
        String[] split = callback.split(":");
        CalendarCommand callbackCommand = CalendarCommand.valueOf(split[0]);
        String callbackDate = split[1];
        BotState currentBotState = cachedUserData.getCurrentBotState();
        switch (callbackCommand) {
            case MONTH -> {
                cachedUserData.setPreviousBotState(currentBotState);
                if (currentBotState == DATE_FROM) cachedUserData.setCurrentBotState(BotState.MONTH_FROM);
                else cachedUserData.setCurrentBotState(BotState.MONTH_TO);
            }
            case PREV_MONTH, NEXT_MONTH -> {
                if (keyboard instanceof KeybordAddData keybordAddData)
                    keybordAddData.addDataToKeyboard(callbackDate);
            }
            case DAY -> {
                LocalDate parsedDate = LocalDate.parse(callbackDate);
                if (currentBotState == DATE_FROM) {
                    cachedUserData.setDateFrom(parsedDate);
                    cachedUserData.setCurrentBotState(BotState.DATE_TO);
                } else {
                    cachedUserData.setDateTo(parsedDate);
                    cachedUserData.setCurrentBotState(BotState.LOAD);
                }
            }
        }
    }
}
