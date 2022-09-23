package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.service.handler.BotStateHandler;
import com.godeltech.gbf.service.handler.impl.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BotStateHandlerFactory {

    private final ApplicationContext applicationContext;

    public BotStateHandlerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BotStateHandler getHandler(State state) {
        return switch (state) {
            case MENU -> applicationContext.getBean(MenuStateHandler.class);
            case REGISTRATIONS -> applicationContext.getBean(RegistrationsStateHandler.class);
            case DATE_FROM, DATE_TO -> applicationContext.getBean(DateStateHandler.class);
            case YEAR_FROM, YEAR_TO -> applicationContext.getBean(YearStateHandler.class);
            case MONTH_FROM, MONTH_TO -> applicationContext.getBean(MonthStateHandler.class);
            case COUNTRY_FROM, COUNTRY_TO -> applicationContext.getBean(CountryStateHandler.class);
            case CITY_FROM, CITY_TO -> applicationContext.getBean(CityStateHandler.class);
            case CARGO -> applicationContext.getBean(CargoStateHandler.class);
            case WRONG_INPUT -> applicationContext.getBean(WrongInputStateHandler.class);
            case CONFIRMATION -> applicationContext.getBean(ConfirmStateHandler.class);
            case SUCCESS -> applicationContext.getBean(SuccessStateHandler.class);
            case USERS_LIST -> applicationContext.getBean(UsersListStateHandler.class);
            case PACKAGE -> applicationContext.getBean(PackageStateHandler.class);
//            case PEOPLE -> applicationContext.getBean()
            default -> null;
        };
    }
}
