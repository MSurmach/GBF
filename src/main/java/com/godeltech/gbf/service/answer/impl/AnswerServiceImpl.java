package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.*;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.model.BotStateFlow.COURIER;

@Service
public class AnswerServiceImpl extends LocalAnswerService {

    protected AnswerServiceImpl(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Override
    public String getTextAnswer(UserData userData) {
        BotState currentBotState = userData.getCurrentBotState();
        BotStateFlow currentBotStateFlow = userData.getBotStateFlow();
        return switch (currentBotState) {
            case MENU -> localeMessageSource.getLocaleMessage("menu", userData.getUsername());
            case CONFIRM -> currentBotStateFlow == COURIER ?
                    constructConfirmationMessageForCourier(userData) :
                    constructConfirmationMessageForCustomer(userData);
            case COUNTRY_TO -> localeMessageSource.getLocaleMessage("country.to");
            case COUNTRY_FROM -> localeMessageSource.getLocaleMessage("country.from");
            case CITY_FROM -> localeMessageSource.getLocaleMessage("city.from", userData.getCountryFrom());
            case CITY_TO -> localeMessageSource.getLocaleMessage("city.to", userData.getCountryTo());
            case YEAR_FROM ->
                    localeMessageSource.getLocaleMessage("year.to", userData.getCountryTo(), userData.getCityTo());
            case YEAR_TO ->
                    localeMessageSource.getLocaleMessage("year.from", userData.getCountryFrom(), userData.getCityFrom());
            case DAY_TO ->
                    localeMessageSource.getLocaleMessage("day.to", userData.getCountryTo(), userData.getCityTo());
            case DAY_FROM ->
                    localeMessageSource.getLocaleMessage("day.from", userData.getCountryFrom(), userData.getCityFrom());
            case LOAD -> currentBotStateFlow == COURIER ?
                    localeMessageSource.getLocaleMessage("load.courier") :
                    localeMessageSource.getLocaleMessage("load.customer");
            case MONTH_FROM ->
                    localeMessageSource.getLocaleMessage("month.to", userData.getCountryTo(), userData.getCityTo());
            case MONTH_TO ->
                    localeMessageSource.getLocaleMessage("month.from", userData.getCountryFrom(), userData.getCityFrom());
            case REGISTRATIONS -> constructRegistrationsMessage(userData, null);
            case SUCCESS -> localeMessageSource.getLocaleMessage("courier.registration.success");
            case USERS_LIST -> constructUsersListMessage(null);
            case WRONG_INPUT -> constructWrongInputMessage(userData);
            case DATE_FROM ->
                    localeMessageSource.getLocaleMessage("date.from", userData.getCountryFrom(), userData.getCityFrom(), LocalDate.now().format(dateTimeFormatter));
            case DATE_TO ->
                    localeMessageSource.getLocaleMessage("date.to", userData.getCountryTo(), userData.getCityTo(), LocalDate.now().format(dateTimeFormatter));
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
                userData.getCountryFrom(),
                userData.getCityFrom(),
                userData.getDateFrom().format(dateTimeFormatter),
                userData.getCountryTo(),
                userData.getCityTo(),
                userData.getDateTo().format(dateTimeFormatter),
                userData.getLoad().name());
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
