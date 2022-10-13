package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class FormMessage implements Message {
    public final static String COURIER_INSTRUCTION_CODE = "form.courier.instruction";
    public final static String COURIER_ESSENTIAL_INFO_CODE = "form.courier.essential.info";
    public final static String CLIENT_INSTRUCTION_CODE = "form.client.instruction";
    public final static String CLIENT_ESSENTIAL_INFO_CODE = "form.client.essential.info";
    public final static String DETAILS_HEADER_EMPTY_CODE = "form.details.header.empty";
    public final static String DETAILS_HEADER_FULL_CODE = "form.details.header.full";
    public final static String CARGO_DOCUMENTS_SELECTED_CODE = "cargo.documents.selected";
    public final static String CARGO_PACKAGE_SELECTED_CODE = "cargo.package.selected";
    public final static String CARGO_PEOPLE_SELECTED_CODE = "cargo.people.selected";
    public final static String DETAILS_COMMENT_CODE = "form.details.comment";
    public final static String COURIER_DETAILS_CARGO_CODE = "form.courier.details.cargo";
    public final static String CLIENT_DETAILS_CARGO_CODE = "form.client.details.cargo";
    public final static String EMPTY_CODE = "";

    private LocalMessageSource lms;
    private MessageUtil messageUtil;

    @Override
    public String initialMessage(UserData userData) {
        Role currentRole = userData.getRole();
        return switch (currentRole) {
            case COURIER -> lms.getLocaleMessage(COURIER_INSTRUCTION_CODE, userData.getUsername()) +
                    lms.getLocaleMessage(COURIER_ESSENTIAL_INFO_CODE);
            case CLIENT -> lms.getLocaleMessage(CLIENT_INSTRUCTION_CODE, userData.getUsername()) +
                    lms.getLocaleMessage(CLIENT_ESSENTIAL_INFO_CODE);
            case REGISTRATIONS_VIEWER -> null;
            case REQUESTS_VIEWER -> null;
        };
    }

    @Override
    public String getMessage(UserData userData) {
        return buildHeader(userData) +
                messageUtil.buildRoute(userData.getRoutePoints()) +
                buildCargoSummary(userData) +
                buildComment(userData);
    }

    private String buildHeader(UserData userData) {
        return userDataIsEmpty(userData) ?
                lms.getLocaleMessage(DETAILS_HEADER_EMPTY_CODE) :
                lms.getLocaleMessage(DETAILS_HEADER_FULL_CODE);
    }

    private String buildCargoSummary(UserData userData) {
        StringBuilder cargoSummary = new StringBuilder();
        String cargoHeader = switch (userData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(COURIER_DETAILS_CARGO_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(CLIENT_DETAILS_CARGO_CODE);
        };
        if (userData.isDocuments() ||
                userData.getPackageSize() != null ||
                userData.getCompanionCount() != 0)
            cargoSummary.append(cargoHeader);
        if (userData.isDocuments())
            cargoSummary.append(lms.getLocaleMessage(CARGO_DOCUMENTS_SELECTED_CODE));
        if (userData.getPackageSize() != null)
            cargoSummary.append(lms.getLocaleMessage(
                    CARGO_PACKAGE_SELECTED_CODE,
                    userData.getPackageSize()));
        if (userData.getCompanionCount() != 0)
            cargoSummary.append(lms.getLocaleMessage(
                    CARGO_PEOPLE_SELECTED_CODE,
                    String.valueOf(userData.getCompanionCount())));
        return cargoSummary.toString();
    }

    private String buildComment(UserData userData) {
        return userData.getComment() != null ?
                lms.getLocaleMessage(DETAILS_COMMENT_CODE, userData.getComment()) :
                EMPTY_CODE;
    }

    private boolean userDataIsEmpty(UserData userData) {
        return userData.getRoutePoints().isEmpty() &&
                Objects.isNull(userData.getComment()) &&
                Objects.isNull(userData.getPackageSize()) &&
                !userData.isDocuments() &&
                userData.getCompanionCount() == 0;
    }
}
