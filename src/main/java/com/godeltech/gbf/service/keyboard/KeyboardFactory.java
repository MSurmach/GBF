package com.godeltech.gbf.service.keyboard;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.BotState;

import java.util.HashMap;
import java.util.Map;

public class KeyboardFactory {
    private LocaleMessageSource localeMessageSource;
    private static Map<BotState, KeyBoard> keyBoards = new HashMap<>();


    public KeyboardFactory() {
        //keyBoards.put(MAIN_MENU, new MainMenuKeyboard(localeMessageSource));
    }

    public KeyBoard getKeyboard(BotState botState) {
        return keyBoards.get(botState);
    }
}
