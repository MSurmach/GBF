package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateKeyboardFactory implements Factory<Keyboard> {
    private BeanFactory beanFactory;

    @Override
    public Keyboard get(State state) {
        Class<? extends Keyboard> keyboard =
                switch (state) {
                    case MENU -> MenuKeyboard.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryKeyboard.class;
                    case CITY_FROM, CITY_TO -> CityKeyboard.class;
                    case YEAR_FROM, YEAR_TO -> YearKeyboard.class;
                    case MONTH_FROM, MONTH_TO -> MonthKeyboard.class;
                    case DATE_FROM, DATE_TO -> DateKeyboard.class;
                    case CARGO_MENU -> CargoMenuKeyboard.class;
                    case CARGO_PACKAGE -> CargoPackageKeyboard.class;
                    case COMMENT_QUIZ -> CommentKeyboard.class;
                    case COMMENT_CONFIRM -> CommentConfirmationKeyboard.class;
                    case CONFIRMATION -> ConfirmKeyboard.class;
                    case SUCCESS -> BackMenuKeyboard.class;
                    default -> EmptyKeyboard.class;
                };
        return beanFactory.getBean(keyboard);
    }
}
