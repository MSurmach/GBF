package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CargoMenuAnswer implements Answer {
    public final static String CARGO_MENU_COURIER_CODE = "cargo.menu.courier";
    public final static String CARGO_MENU_CUSTOMER_CODE = "cargo.menu.customer";
    public final static String CARGO_MENU_SELECTED_CODE = "cargo.selected";
    public final static String CARGO_MENU_NOT_SELECTED_CODE = "cargo.notSelected";
    public final static String CARGO_DOCUMENTS_SELECTED = "cargo.documents.selected";
    public final static String CARGO_PACKAGE_SELECTED = "cargo.package.selected";
    public final static String CARGO_PEOPLE_SELECTED = "cargo.people.selected";
    public final static String EMPTY = "";
    private final LocalMessageSource localMessageSource;

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        String selectedContent = buildSelectedContent(userData);
        String selectedCode = selectedCode(userData);
        String selectedAnswer = localMessageSource.getLocaleMessage(selectedCode, selectedContent);
        StateFlow stateFlow = userData.getStateFlow();
        return stateFlow == StateFlow.COURIER ?
                selectedAnswer + System.lineSeparator() + localMessageSource.getLocaleMessage(CARGO_MENU_COURIER_CODE) :
                selectedAnswer + System.lineSeparator() + localMessageSource.getLocaleMessage(CARGO_MENU_CUSTOMER_CODE);
    }

    private String buildSelectedContent(UserData userData) {
        return documentsRecord(userData) + packageRecord(userData) + companionRecord(userData);
    }

    private String selectedCode(UserData userData) {
        String selected;
        if (!userData.isDocuments() &&
                userData.getPackageSize() == null &&
                userData.getCompanionCount() == 0)
            selected = CARGO_MENU_NOT_SELECTED_CODE;
        else selected = CARGO_MENU_SELECTED_CODE;
        return selected;
    }

    private String documentsRecord(UserData userData) {
        return userData.isDocuments() ?
                localMessageSource.getLocaleMessage(CARGO_DOCUMENTS_SELECTED) : EMPTY;
    }

    private String packageRecord(UserData userData) {
        String packageSize = userData.getPackageSize();
        return packageSize != null ?
                localMessageSource.getLocaleMessage(CARGO_PACKAGE_SELECTED, packageSize) :
                EMPTY;
    }

    private String companionRecord(UserData userData) {
        Integer companionCount = userData.getCompanionCount();
        return companionCount != 0 ?
                localMessageSource.getLocaleMessage(CARGO_PEOPLE_SELECTED, companionCount.toString()) :
                EMPTY;
    }
}
