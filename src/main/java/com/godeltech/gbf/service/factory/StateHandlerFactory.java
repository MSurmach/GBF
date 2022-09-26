package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.handler.impl.*;
import lombok.AllArgsConstructor;
import org.apache.naming.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateHandlerFactory implements Factory<StateHandler>{

    private final BeanFactory beanFactory;

    public StateHandler getHandler(State state) {
        return switch (state) {
            case MENU -> applicationContext.getBean(MenuStateHandler.class);
            case REGISTRATIONS -> applicationContext.getBean(RegistrationsStateHandler.class);
            case DATE_FROM, DATE_TO -> applicationContext.getBean(DateStateHandler.class);
            case YEAR_FROM, YEAR_TO -> applicationContext.getBean(YearStateHandler.class);
            case MONTH_FROM, MONTH_TO -> applicationContext.getBean(MonthStateHandler.class);
            case COUNTRY_TO, COUNTRY_TO -> applicationContext.getBean(CountryStateHandler.class);
            case CITY_FROM, CITY_TO -> applicationContext.getBean(CityStateHandler.class);
            case CARGO_MENU -> applicationContext.getBean(CargoStateHandler.class);
            case WRONG_INPUT -> applicationContext.getBean(WrongInputStateHandler.class);
            case CONFIRMATION -> applicationContext.getBean(ConfirmStateHandler.class);
            case SUCCESS -> applicationContext.getBean(SuccessStateHandler.class);
            case USERS_LIST -> applicationContext.getBean(UsersListStateHandler.class);
            case CARGO_PACKAGE -> applicationContext.getBean(PackageStateHandler.class);
            case CARGO_PEOPLE -> applicationContext.getBean(PeopleStateHandler.class);
            default -> null;
        };
    }

    @Override
    public StateHandler get(State state) {
        return null;
    }
}
