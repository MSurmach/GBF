package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.handler.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateHandlerFactory implements Factory<StateHandler> {

    private final BeanFactory beanFactory;

    @Override
    public StateHandler get(State state) {
        Class<? extends StateHandler> handler =
                switch (state) {
                    case MENU -> MenuStateHandler.class;
                    case DATE_FROM, DATE_TO -> DateStateHandler.class;
                    case YEAR_FROM, YEAR_TO -> YearStateHandler.class;
                    case MONTH_FROM, MONTH_TO -> MonthStateHandler.class;
                    case COMMENT_QUIZ -> CommentQuizStateHandler.class;
                    case COMMENT -> CommentStateHandler.class;
                    case COMMENT_CONFIRM -> CommentConfirmStateHandler.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryStateHandler.class;
                    case CITY_FROM, CITY_TO -> CityStateHandler.class;
                    case CARGO_MENU -> CargoMenuStateHandler.class;
                    case CARGO_PACKAGE -> CargoPackageStateHandler.class;
                    case CARGO_PEOPLE -> CargoPeopleStateHandler.class;
                    case WRONG_INPUT -> WrongInputStateHandler.class;
                    case REGISTRATIONS -> RegistrationsStateHandler.class;
                    case REGISTRATION_EDITOR -> RegistrationEditorStateHandler.class;
                    case DATE_TO_QUIZ, DATE_FROM_QUIZ -> DateQuizStateHandler.class;
                    case FOUND_COURIERS_INFO -> FoundCouriersInfoStateHandler.class;
                    case SUMMARY_DATA_TO_CONFIRM -> SummaryStateHandler.class;
                    case COURIERS_LIST -> CouriersListStateHandler.class;
                    default -> throw new IllegalArgumentException("Handler not found for this state");
                };
        return beanFactory.getBean(handler);
    }
}
