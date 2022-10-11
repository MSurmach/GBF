package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.gui.message.CommonMessageCode.*;

@Service
@AllArgsConstructor
public class SummaryDataMessage implements Message {
    private final LocalMessageSource localMessageSource;
    public final static String CARGO_DOCUMENTS_SELECTED = "cargo.documents.selected";
    public final static String CARGO_PACKAGE_SELECTED = "cargo.package.selected";
    public final static String CARGO_PEOPLE_SELECTED = "cargo.people.selected";
    public final static String EMPTY = "";

    @Override
    public String getMessage(UserData userData) {
        StringBuilder summary = new StringBuilder();
        String startCityCountry = localMessageSource.getLocaleMessage(
                COUNTRY_CITY_START_CODE.getCode(),
                localMessageSource.getLocaleMessage(userData.getCountryFrom()),
                localMessageSource.getLocaleMessage(userData.getCityFrom()));
        String finishCityCountry = localMessageSource.getLocaleMessage(
                COUNTRY_CITY_FINISH_CODE.getCode(),
                localMessageSource.getLocaleMessage(userData.getCountryTo()),
                localMessageSource.getLocaleMessage(userData.getCityTo()));
        summary.append(startCityCountry).append(finishCityCountry);
        if (userData.getDateFrom() != null)
            summary.append(date(userData.getDateFrom(), DATE_START_CODE.getCode()));
        if (userData.getDateTo() != null)
            summary.append(date(userData.getDateTo(), DATE_FINISH_CODE.getCode()));
        String cargoHeader = localMessageSource.getLocaleMessage(CARGO_DATA_CODE.getCode());
        summary.append(cargoHeader);
        summary.append(cargoSummary(userData));
        if (userData.getComment() != null) {
            summary.append(comment(userData.getComment()));
        }
        return summary.toString();
    }

    String cargoSummary(UserData userData) {
        StringBuilder cargoSummary = new StringBuilder();
        if (userData.isDocuments())
            cargoSummary.append(documents());
        if (userData.getPackageSize() != null)
            cargoSummary.append(packageSize(userData.getPackageSize()));
        if (userData.getCompanionCount() != 0)
            cargoSummary.append(companions(userData.getCompanionCount()));
        return cargoSummary.toString();
    }

    private String date(LocalDate date, String code) {
        return localMessageSource.getLocaleMessage(code, DateUtils.formatDate(date, localMessageSource.getLocale()));
    }

    private String comment(String comment) {
        return localMessageSource.getLocaleMessage(COMMENT_CONTENT_CODE.getCode(), comment);
    }

    private String documents() {
        return localMessageSource.getLocaleMessage(CARGO_DOCUMENTS_SELECTED);
    }

    private String packageSize(String packageSize) {
        return localMessageSource.getLocaleMessage(CARGO_PACKAGE_SELECTED, packageSize);
    }

    private String companions(int companionCount) {
        return localMessageSource.getLocaleMessage(CARGO_PEOPLE_SELECTED, String.valueOf(companionCount));
    }
}
