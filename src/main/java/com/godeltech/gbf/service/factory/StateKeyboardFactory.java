package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.model.State;
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
                    case MENU -> MainMenuKeyboard.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryKeyboard.class;
                    case CITY_FROM, CITY_TO -> CityKeyboard.class;
                    case YEAR_FROM, YEAR_TO -> YearKeyboard.class;
                    case MONTH_FROM, MONTH_TO -> MonthKeyboard.class;
                    case DATE_FROM, DATE_TO -> DateKeyboard.class;
                    case DATE_FROM_QUIZ, DATE_TO_QUIZ -> DateQuizKeyboard.class;
                    case CARGO_MENU -> CargoMainKeyboard.class;
                    case CARGO_PACKAGE -> CargoPackageKeyboard.class;
                    case COMMENT_QUIZ -> CommentKeyboard.class;
                    case COMMENT_CONFIRM -> CommentConfirmationKeyboard.class;
                    case SUMMARY_DATA_TO_CONFIRM -> ConfirmKeyboard.class;
                    case SUCCESS -> BackMenuKeyboard.class;
                    case REGISTRATION_EDITOR -> RegistrationEditorKeyboard.class;
                    case FOUND_COURIERS_INFO -> FoundCouriersInfoKeyboard.class;
                    default -> EmptyKeyboard.class;
                };
        return beanFactory.getBean(keyboard);
    }
}
