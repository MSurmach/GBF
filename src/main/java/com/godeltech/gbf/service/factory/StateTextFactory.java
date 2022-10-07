package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.text.Text;
import com.godeltech.gbf.service.text.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateTextFactory implements Factory<Text> {
    private BeanFactory beanFactory;

    @Override
    public Text get(State state) {
        Class<? extends Text> answer =
                switch (state) {
                    case MENU -> MenuText.class;
                    case WRONG_INPUT -> WrongInputText.class;
                    case SUCCESS -> SuccessText.class;
                    case COMMENT_QUIZ -> CommentQuizText.class;
                    case COMMENT -> CommentText.class;
                    case COMMENT_CONFIRM -> CommentConfirmText.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryText.class;
                    case CITY_FROM, CITY_TO -> CityText.class;
                    case YEAR_FROM, YEAR_TO -> YearText.class;
                    case MONTH_FROM, MONTH_TO -> MonthText.class;
                    case DATE_FROM, DATE_TO -> DateText.class;
                    case DATE_FROM_QUIZ, DATE_TO_QUIZ -> DateQuizText.class;
                    case CARGO_MENU -> CargoMenuText.class;
                    case CARGO_PACKAGE -> CargoPackageText.class;
                    case CARGO_PEOPLE -> CargoPeopleText.class;
                    case REGISTRATION_EDITOR -> RegistrationEditorText.class;
                    case FOUND_COURIERS_INFO -> FoundCouriersInfoText.class;
                    case SUMMARY_DATA_TO_CONFIRM -> SummaryConfirmationText.class;
                    case REGISTRATIONS -> RegistrationsText.class;
                    case COURIERS_LIST -> CouriersListText.class;
                    case CLIENTS_LIST -> ClientsListText.class;
                    case REQUESTS -> RequestsText.class;
                    default -> throw new IllegalArgumentException("Text not found");
                };
        return beanFactory.getBean(answer);
    }
}
