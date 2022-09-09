package com.godeltech.gbf.service.keyboard;

import com.godeltech.gbf.service.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.impl.*;
import com.godeltech.gbf.state.BotState;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class KeyboardFactory {

    private LocaleMessageSource localeMessageSource;
    private static KeyboardFactory keyboardFactory;

    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    private KeyboardFactory() {
    }

    public static KeyboardFactory getKeyboardFactory() {
        return Objects.isNull(keyboardFactory) ? new KeyboardFactory() : keyboardFactory;
    }


    public Keyboard getKeyboard(BotState botState) {
        return switch (botState) {
            case START -> new StartKeyboard(localeMessageSource);
            case COUNTRY_FROM, COUNTRY_TO -> new CountryKeyboard(localeMessageSource);
            case CITY_FROM, CITY_TO -> new CityKeyboard(localeMessageSource);
            case DATE_FROM, DATE_TO -> new DateKeyboard(localeMessageSource);
            case CARGO -> new CargoKeyboard(localeMessageSource);
            default -> null;
        };
    }
}
