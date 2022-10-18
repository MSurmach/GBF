package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.impl.*;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KeyboardFactory implements Factory<Keyboard> {
    private BeanFactory beanFactory;

    @Override
    public Keyboard get(State state) {
        Class<? extends Keyboard> keyboard =
                switch (state) {
                    case MENU -> MainMenuKeyboard.class;
                    case COUNTRY -> CountryKeyboard.class;
                    case CITY -> CityKeyboard.class;
                    case YEAR -> YearKeyboard.class;
                    case MONTH -> MonthKeyboard.class;
                    case DATE -> DateKeyboard.class;
                    case CARGO_MENU -> CargoMainKeyboard.class;
                    case CARGO_PACKAGE -> CargoPackageKeyboard.class;
                    case CARGO_PEOPLE -> ControlKeyboard.class;
                    case SUCCESS -> BackMenuKeyboard.class;
                    case REGISTRATIONS -> RegistrationKeyboard.class;
                    case REQUESTS -> RequestKeyboard.class;
                    case FORM -> FormKeyboard.class;
                    case ROUTE_POINT_FORM -> RoutePointFormKeyboard.class;
                    case INTERMEDIATE_EDITOR -> IntermediateEditorKeyboard.class;
                    default -> EmptyKeyboard.class;
                };
        return beanFactory.getBean(keyboard);
    }
}
