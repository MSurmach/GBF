package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CargoValidator {
    public final static String ALERT_CARGO_MENU_NOTHING_SELECTED_CODE = "alert.cargoMenu.nothingSelected";
    private LocalMessageSource lms;

    public void checkIfCargoIsEmpty(UserData userData) {
        String alertMessage = lms.getLocaleMessage(ALERT_CARGO_MENU_NOTHING_SELECTED_CODE);
        String callbackQueryId = userData.getCallbackQueryId();
        if (userData.isEmpty()) throw new GbfException(callbackQueryId, alertMessage);
    }
}
