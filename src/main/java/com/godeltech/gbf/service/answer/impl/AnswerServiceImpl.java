package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.controls.Command;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.controls.StateFlow;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.controls.StateFlow.COURIER;

@Service
public class AnswerServiceImpl extends LocalAnswerService {
    private final DateTimeFormatter dateTimeFormatter;


    public AnswerServiceImpl(LocalMessageSource localMessageSource) {
        super(localMessageSource);
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(localMessageSource.getLocale());
    }

    @Override
    public String getTextAnswer(UserData userData) {
        State currentState = userData.getCurrentState();
        StateFlow currentStateFlow = userData.getStateFlow();
        return switch (currentState) {
            case MENU -> localMessageSource.getLocaleMessage("menu", userData.getUsername());
            case CONFIRMATION -> currentStateFlow == COURIER ?
                    constructConfirmationMessageForCourier(userData) :
                    constructConfirmationMessageForCustomer(userData);
            case COUNTRY_TO -> localMessageSource.getLocaleMessage("country.to");
            case COUNTRY_FROM -> localMessageSource.getLocaleMessage("country.from");
            case CITY_FROM -> {
                String localeCountryFrom = localMessageSource.getLocaleMessage(userData.getCountryFrom());
                yield localMessageSource.getLocaleMessage("city.from", localeCountryFrom);
            }
            case CITY_TO -> {
                String localeCountryTo = localMessageSource.getLocaleMessage(userData.getCountryTo());
                yield localMessageSource.getLocaleMessage("city.to", localeCountryTo);
            }
            case YEAR_FROM, YEAR_TO -> localMessageSource.getLocaleMessage("year");
            case CARGO -> currentStateFlow == COURIER ?
                    localMessageSource.getLocaleMessage("CARGO.courier") :
                    localMessageSource.getLocaleMessage("CARGO.customer");
            case MONTH_FROM, MONTH_TO -> localMessageSource.getLocaleMessage("month");
            case REGISTRATIONS -> constructRegistrationsMessage(userData, null);
            case SUCCESS -> localMessageSource.getLocaleMessage("courier.registration.success");
            case USERS_LIST -> constructUsersListMessage(null);
            case WRONG_INPUT -> constructWrongInputMessage(userData);
            case DATE_FROM -> {
                String localeCountryFrom = localMessageSource.getLocaleMessage(userData.getCountryFrom());
                String localeCityFrom = localMessageSource.getLocaleMessage(userData.getCityFrom());
                String localDateNow = LocalDate.now().format(dateTimeFormatter);
                yield localMessageSource.getLocaleMessage("date.from", localeCountryFrom, localeCityFrom, localDateNow);
            }
            case DATE_TO -> {
                String localeCountryTo = localMessageSource.getLocaleMessage(userData.getCountryTo());
                String localeCityTo = localMessageSource.getLocaleMessage(userData.getCityTo());
                String localDateNow = LocalDate.now().format(dateTimeFormatter);
                yield localMessageSource.getLocaleMessage("date.to", localeCountryTo, localeCityTo, localDateNow);
            }
            default -> null;
        };
    }

    private String constructUsersListMessage(List<User> users) {

        StringBuilder stringBuilder = new StringBuilder();
        users.forEach(user -> {
            String record = localMessageSource.getLocaleMessage("list.user.data",
                    user.getUsername(),
                    user.getCountryFrom(),
                    user.getCityFrom(),
                    user.getDateFrom().format(dateTimeFormatter),
                    user.getCountryTo(),
                    user.getCityTo(),
                    user.getDateTo().format(dateTimeFormatter));
//                    user.getCARGO().name());
            stringBuilder.append(record).append("\n\n");
        });
        return stringBuilder.toString();
    }

    private String constructConfirmationMessageForCourier(UserData userData) {
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(localMessageSource.getLocaleMessage("confirm.courier", username));
        stringBuilder.append("\n\n");
        String confirmationData = localMessageSource.getLocaleMessage("courier.user.data",
                localMessageSource.getLocaleMessage(userData.getCountryFrom()),
                localMessageSource.getLocaleMessage(userData.getCityFrom()),
                userData.getDateFrom().format(dateTimeFormatter),
                localMessageSource.getLocaleMessage(userData.getCountryTo()),
                localMessageSource.getLocaleMessage(userData.getCityTo()),
                userData.getDateTo().format(dateTimeFormatter));
        //userData.getLoadCategory().getLocalDescription(localMessageSource));
        stringBuilder.append(confirmationData);
        return stringBuilder.toString();
    }

    private String constructConfirmationMessageForCustomer(UserData userData) {
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(localMessageSource.getLocaleMessage("confirm.customer", username));
        stringBuilder.append("\n\n");
        String confirmationData = localMessageSource.getLocaleMessage("customer.user.data",
                userData.getCountryFrom(),
                userData.getCityFrom(),
                userData.getCountryTo(),
                userData.getCityTo());
        //userData.getLoadCategory().name());
        stringBuilder.append(confirmationData);
        return stringBuilder.toString();
    }

    private String constructRegistrationsMessage(UserData userData, List<User> registrations) {
        String username = userData.getUsername();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String mainMessage = registrations.isEmpty() ?
                localMessageSource.getLocaleMessage("registrations.not_exist", username) :
                localMessageSource.getLocaleMessage("registrations.exist", username);
        StringBuilder stringBuilder = new StringBuilder(mainMessage).append("\n\n");
        registrations.forEach(registration -> {
            String record = localMessageSource.getLocaleMessage("registration.data",
                    registration.getId().toString(),
                    registration.getCountryFrom(),
                    registration.getCityFrom(),
                    registration.getDateFrom().format(dateTimeFormatter),
                    registration.getCountryTo(),
                    registration.getCityTo(),
                    registration.getDateTo().format(dateTimeFormatter));
            //registration.getCARGO().name());
            stringBuilder.append(record).append("\n\n");
        });
        return stringBuilder.toString();
    }

    private String constructWrongInputMessage(UserData userData) {
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        String headerMessage = localMessageSource.getLocaleMessage("wrong_input", username);
        stringBuilder.append(headerMessage).append(System.lineSeparator());
        Arrays.asList(Command.Text.values()).
                forEach(textCommand -> stringBuilder.append(textCommand.getText()).
                        append(System.lineSeparator()));
        return stringBuilder.toString();
    }

}
