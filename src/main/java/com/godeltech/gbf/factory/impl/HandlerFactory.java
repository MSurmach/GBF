package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.handler.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HandlerFactory implements Factory<Handler> {

    private final BeanFactory beanFactory;

    @Override
    public Handler get(State state) {
        Class<? extends Handler> handler =
                switch (state) {
                    case MENU -> MenuHandler.class;
                    case DATE_FROM, DATE_TO -> DateHandler.class;
                    case YEAR_FROM, YEAR_TO -> YearHandler.class;
                    case MONTH_FROM, MONTH_TO -> MonthHandler.class;
                    case COMMENT_QUIZ -> CommentQuizHandler.class;
                    case COMMENT -> CommentHandler.class;
                    case COMMENT_CONFIRM -> CommentConfirmHandler.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryHandler.class;
                    case CITY_FROM, CITY_TO -> CityHandler.class;
                    case CARGO_MENU -> CargoMenuHandler.class;
                    case CARGO_PACKAGE -> CargoPackageHandler.class;
                    case CARGO_PEOPLE -> CargoPeopleHandler.class;
                    case WRONG_INPUT -> WrongInputHandler.class;
                    case REGISTRATIONS -> RegistrationsHandler.class;
                    case REGISTRATION_EDITOR -> EditorHandler.class;
                    case DATE_TO_QUIZ, DATE_FROM_QUIZ -> DateQuizHandler.class;
                    case SUMMARY_DATA_TO_CONFIRM -> SummaryHandler.class;
                    case REQUESTS -> RequestsHandler.class;
                    case FORM -> FormHandler.class;
                    case ROUTE_POINT_FORM -> RoutePointFormHandler.class;
                    default -> throw new IllegalArgumentException("Handler not found for this state");
                };
        return beanFactory.getBean(handler);
    }
}
