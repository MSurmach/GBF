package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.text.Text;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.COURIER;

@Service
@AllArgsConstructor
public class CargoMenuText implements Text {
    public final static String CARGO_MENU_COURIER_CODE = "courier.cargo.menu";
    public final static String CARGO_MENU_CUSTOMER_CODE = "customer.cargo.menu";
    public final static String CARGO_MENU_SELECTED_CODE = "cargo.selected";
    public final static String CARGO_MENU_NOT_SELECTED_CODE = "cargo.notSelected";
    private final LocalMessageSource localMessageSource;
    private final SummaryDataText summaryDataText;

    @Override
    public String getText(UserData userData) {
        String question = userData.getRole() == COURIER ?
                localMessageSource.getLocaleMessage(CARGO_MENU_COURIER_CODE) :
                localMessageSource.getLocaleMessage(CARGO_MENU_CUSTOMER_CODE);
        String cargoSelectedInfo = cargoSelectedInfo(userData);
        String cargoSummary = summaryDataText.cargoSummary(userData);
        return question + cargoSelectedInfo + cargoSummary;
    }

    private String cargoSelectedInfo(UserData userData) {
        return (!userData.isDocuments() &&
                userData.getPackageSize() == null &&
                userData.getCompanionCount() == 0) ?
                localMessageSource.getLocaleMessage(CARGO_MENU_NOT_SELECTED_CODE) :
                localMessageSource.getLocaleMessage(CARGO_MENU_SELECTED_CODE);

    }
}
