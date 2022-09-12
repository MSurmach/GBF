package com.godeltech.gbf.service.stateHandler;

import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.service.stateHandler.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BotStateHandlerFactory {

    private ApplicationContext applicationContext;

    @Autowired
    public BotStateHandlerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BotStateHandler getHandler(BotState botState) {
        return switch (botState) {
            case INIT -> applicationContext.getBean(InitStateHandler.class);
            case REGISTRATIONS -> applicationContext.getBean(RegistrationsStateHandler.class);
            case YEAR -> applicationContext.getBean(YearStateHandler.class);
            case MONTH -> applicationContext.getBean(MonthStateHandler.class);
            case DAY -> applicationContext.getBean(DayStateHandler.class);
            case COUNTRY -> applicationContext.getBean(CountryStateHandler.class);
            case CITY -> applicationContext.getBean(CityStateHandler.class);
            case CARGO -> applicationContext.getBean(CargoStateHandler.class);
            case FINISH -> null;
            case HELP -> applicationContext.getBean(HelpStateHandler.class);
        };
    }
}
