package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CargoValidator {
    public final static String ALERT_CARGO_EMPTY_CODE = "alert.cargo.empty";
    private LocalMessageSource lms;

    public void checkIfCargoIsEmpty(SessionData sessionData) {
        String alertMessage = lms.getLocaleMessage(ALERT_CARGO_EMPTY_CODE);
        String callbackQueryId = sessionData.getCallbackQueryId();
//        if (userData.isCargoEmpty()) throw new GbfException(callbackQueryId, alertMessage);
    }
}
