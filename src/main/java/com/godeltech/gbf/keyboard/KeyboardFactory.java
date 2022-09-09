package com.godeltech.gbf.keyboard;

import com.godeltech.gbf.keyboard.impl.*;
import com.godeltech.gbf.state.BotState;

import java.util.Objects;

public class KeyboardFactory {

    private static KeyboardFactory viewFactory;

    private KeyboardFactory() {

    }

    public static KeyboardFactory getKeyboardMarkupFactory() {
        return Objects.isNull(viewFactory) ? new KeyboardFactory() : viewFactory;
    }

    public Keyboard getKeyboard(BotState botState) {
        return switch (botState) {
            case START -> new StartKeyboard();
            case COUNTRY_FROM, COUNTRY_TO -> new CountryKeyboard();
            case CITY_FROM, CITY_TO -> new CityKeyboard();
            case DATE_FROM, DATE_TO -> new DateKeyboard();
            case CARGO -> new CargoKeyboard();
            default -> null;
        };
    }
}
