package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.*;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.model.StateFlow.COURIER;

@Service
public class AnswerServiceImpl extends LocalAnswerService {
    private final DateTimeFormatter dateTimeFormatter;


    public AnswerServiceImpl(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(localeMessageSource.getLocale());
    }

    @Override
    public String getTextAnswer(UserData userData) {
        State currentState = userData.getCurrentState();
        StateFlow currentStateFlow = userData.getStateFlow();
        return switch (currentState) {
            case MENU -> localeMessageSource.getLocaleMessage("menu", userData.getUsername());
            case CONFIRM -> currentStateFlow == COURIER ?
                    constructConfirmationMessageForCourier(userData) :
                    constructConfirmationMessageForCustomer(userData);
            case COUNTRY_TO -> localeMessageSource.getLocaleMessage("country.to");
            case COUNTRY_FROM -> localeMessageSource.getLocaleMessage("country.from");
            case CITY_FROM -> {
                String localeCountryFrom = localeMessageSource.getLocaleMessage(userData.getCountryFrom());
                yield localeMessageSource.getLocaleMessage("city.from", localeCountryFrom);
            }
            case CITY_TO -> {
                String localeCountryTo = localeMessageSource.getLocaleMessage(userData.getCountryTo());
                yield localeMessageSource.getLocaleMessage("city.to", localeCountryTo);
            }
            case YEAR_FROM, YEAR_TO ->
                    localeMessageSource.getLocaleMessage("year");
            case LOAD -> currentStateFlow == COURIER ?
                    localeMessageSource.getLocaleMessage("load.courier") :
                    localeMessageSource.getLocaleMessage("load.customer");
            case MONTH_FROM, MONTH_TO ->
                    localeMessageSource.getLocaleMessage("month");
            case REGISTRATIONS -> constructRegistrationsMessage(userData, null);
            case SUCCESS -> localeMessageSource.getLocaleMessage("courier.registration.success");
            case USERS_LIST -> constructUsersListMessage(null);
            case WRONG_INPUT -> constructWrongInputMessage(userData);
            case DATE_FROM -> {
                String localeCountryFrom = localeMessageSource.getLocaleMessage(userData.getCountryFrom());
                String localeCityFrom = localeMessageSource.getLocaleMessage(userData.getCityFrom());
                String localDateNow = LocalDate.now().format(dateTimeFormatter);
                yield localeMessageSource.getLocaleMessage("date.from", localeCountryFrom, localeCityFrom, localDateNow);
            }
            case DATE_TO -> {
                String localeCountryTo = localeMessageSource.getLocaleMessage(userData.getCountryTo());
                String localeCityTo = localeMessageSource.getLocaleMessage(userData.getCityTo());
                String localDateNow = LocalDate.now().format(dateTimeFormatter);
                yield localeMessageSource.getLocaleMessage("date.from", localeCountryTo, localeCityTo, localDateNow);
            }
            default -> null;
        };
    }

    private String constructUsersListMessage(List<User> users) {

        StringBuilder stringBuilder = new StringBuilder();
        users.forEach(user -> {
            String record = localeMessageSource.getLocaleMessage("list.user.data",
                    user.getUsername(),
                    user.getCountryFrom(),
                    user.getCityFrom(),
                    user.getDateFrom().format(dateTimeFormatter),
                    user.getCountryTo(),
                    user.getCityTo(),
                    user.getDateTo().format(dateTimeFormatter),
                    user.getLoad().name());
            stringBuilder.append(record).append("\n\n");
        });
        return stringBuilder.toString();
    }

    private String constructConfirmationMessageForCourier(UserData userData) {
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(localeMessageSource.getLocaleMessage("confirm.courier", username));
        stringBuilder.append("\n\n");
        String confirmationData = localeMessageSource.getLocaleMessage("courier.user.data",
                localeMessageSource.getLocaleMessage(userData.getCountryFrom()),
                localeMessageSource.getLocaleMessage(userData.getCityFrom()),
                userData.getDateFrom().format(dateTimeFormatter),
                localeMessageSource.getLocaleMessage(userData.getCountryTo()),
                localeMessageSource.getLocaleMessage(userData.getCityTo()),
                userData.getDateTo().format(dateTimeFormatter),
                userData.getLoad().getDescription(localeMessageSource));
        stringBuilder.append(confirmationData);
        return stringBuilder.toString();
    }

    private String constructConfirmationMessageForCustomer(UserData userData) {
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(localeMessageSource.getLocaleMessage("confirm.customer", username));
        stringBuilder.append("\n\n");
        String confirmationData = localeMessageSource.getLocaleMessage("customer.user.data",
                userData.getCountryFrom(),
                userData.getCityFrom(),
                userData.getCountryTo(),
                userData.getCityTo(),
                userData.getLoad().name());
        stringBuilder.append(confirmationData);
        return stringBuilder.toString();
    }

    private String constructRegistrationsMessage(UserData userData, List<User> registrations) {
        String username = userData.getUsername();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String mainMessage = registrations.isEmpty() ?
                localeMessageSource.getLocaleMessage("registrations.not_exist", username) :
                localeMessageSource.getLocaleMessage("registrations.exist", username);
        StringBuilder stringBuilder = new StringBuilder(mainMessage).append("\n\n");
        registrations.forEach(registration -> {
            String record = localeMessageSource.getLocaleMessage("registration.data",
                    registration.getId().toString(),
                    registration.getCountryFrom(),
                    registration.getCityFrom(),
                    registration.getDateFrom().format(dateTimeFormatter),
                    registration.getCountryTo(),
                    registration.getCityTo(),
                    registration.getDateTo().format(dateTimeFormatter),
                    registration.getLoad().name());
            stringBuilder.append(record).append("\n\n");
        });
        return stringBuilder.toString();
    }

    private String constructWrongInputMessage(UserData userData) {
        String username = userData.getUsername();
        StringBuilder stringBuilder = new StringBuilder();
        String headerMessage = localeMessageSource.getLocaleMessage("wrong_input", username);
        stringBuilder.append(headerMessage).append(System.lineSeparator());
        Arrays.asList(TextCommand.values()).forEach(textCommand -> stringBuilder.append(textCommand.getText()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

}
