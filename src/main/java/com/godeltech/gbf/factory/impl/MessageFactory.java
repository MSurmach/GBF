package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.gui.message.impl.*;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageFactory implements Factory<Message> {
    private BeanFactory beanFactory;

    @Override
    public Message get(State state) {
        Class<? extends Message> answer =
                switch (state) {
                    case MENU -> MenuMessage.class;
                    case WRONG_INPUT -> WrongInputMessage.class;
                    case SUCCESS -> SuccessMessage.class;
                    case COMMENT_QUIZ -> CommentQuizMessage.class;
                    case COMMENT -> CommentMessage.class;
                    case COMMENT_CONFIRM -> CommentConfirmMessage.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryMessage.class;
                    case CITY_FROM, CITY_TO -> CityMessage.class;
                    case YEAR_FROM, YEAR_TO -> YearMessage.class;
                    case MONTH_FROM, MONTH_TO -> MonthMessage.class;
                    case DATE_FROM, DATE_TO -> DateMessage.class;
                    case DATE_FROM_QUIZ, DATE_TO_QUIZ -> DateQuizMessage.class;
                    case CARGO_MENU -> CargoMenuMessage.class;
                    case CARGO_PACKAGE -> CargoPackageMessage.class;
                    case CARGO_PEOPLE -> CargoPeopleMessage.class;
                    case REGISTRATION_EDITOR, REQUEST_EDITOR -> EditorMessage.class;
                    case FOUND_COURIERS_INFO -> FoundCouriersInfoMessage.class;
                    case SUMMARY_DATA_TO_CONFIRM -> SummaryConfirmationMessage.class;
                    case REGISTRATIONS -> RegistrationsMessage.class;
                    case COURIERS_LIST -> CouriersListMessage.class;
                    case CLIENTS_LIST -> ClientsListMessage.class;
                    case REQUESTS -> RequestsMessage.class;
                    case FORM -> FormMessage.class;
                    case ROUTE_POINT_FORM -> RoutePointFormMessage.class;
                };
        return beanFactory.getBean(answer);
    }
}