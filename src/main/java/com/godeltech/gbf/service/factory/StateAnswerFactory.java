package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.service.answer.Answer;
import com.godeltech.gbf.service.answer.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateAnswerFactory implements Factory<Answer> {
    private BeanFactory beanFactory;

    @Override
    public Answer get(State state) {
        Class<? extends Answer> answer =
                switch (state) {
                    case MENU -> MenuAnswer.class;
                    case CONFIRMATION -> ConfirmationAnswer.class;
                    case WRONG_INPUT -> WrongInputAnswer.class;
                    case SUCCESS -> SuccessAnswer.class;
                    case COMMENT_QUIZ -> CommentQuizAnswer.class;
                    case COMMENT -> CommentAnswer.class;
                    case COMMENT_CONFIRM -> CommentConfirmAnswer.class;
                    case COUNTRY_FROM, COUNTRY_TO -> CountryAnswer.class;
                    case CITY_FROM, CITY_TO -> CityAnswer.class;
                    case YEAR_FROM, YEAR_TO -> YearAnswer.class;
                    case MONTH_FROM, MONTH_TO -> MonthAnswer.class;
                    case DATE_FROM, DATE_TO -> DateAnswer.class;
                    case CARGO_MENU -> CargoMenuAnswer.class;
                    case CARGO_PACKAGE -> CargoPackageAnswer.class;
                    case CARGO_PEOPLE -> CargoPeopleAnswer.class;
                    case REGISTRATION_EDITOR -> RegistrationEditorAnswer.class;
                    default -> throw new IllegalArgumentException("Answer not found");
                };
        return beanFactory.getBean(answer);
    }
}
