package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.model.Role.COURIER;

@Component
@AllArgsConstructor
public class CargoMenuMessage implements Message {
    public final static String CARGO_MENU_COURIER_CODE = "courier.cargo.menu";
    public final static String CARGO_MENU_CUSTOMER_CODE = "customer.cargo.menu";
    public final static String CARGO_MENU_NOT_SELECTED_CODE = "cargo.notSelected";
    public final static String EMPTY = " ";
    private final LocalMessageSource localMessageSource;
    private DetailsCreator detailsCreator;

    @Override
    public String getMessage(UserData userData) {
        String question = userData.getRole() == COURIER ?
                localMessageSource.getLocaleMessage(CARGO_MENU_COURIER_CODE) :
                localMessageSource.getLocaleMessage(CARGO_MENU_CUSTOMER_CODE);
        String cargoSelectedInfo = cargoSelectedInfo(userData);
        String cargoDetails = detailsCreator.createCargoDetails(userData);
        return question + cargoSelectedInfo + cargoDetails;
    }

    private String cargoSelectedInfo(UserData userData) {
        return (!userData.isDocumentsExist() &&
                userData.getPackageSize() == null &&
                userData.getCompanionCount() == 0) ?
                localMessageSource.getLocaleMessage(CARGO_MENU_NOT_SELECTED_CODE) :
                EMPTY;
    }
}
