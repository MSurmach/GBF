package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SummaryConfirmationMessage implements Message {
    public final static String SUMMARY_NOTIFICATION_CODE = "summary.notification";
    private final LocalMessageSource localMessageSource;
    private final SummaryDataMessage summaryDataText;

    @Override
    public String getMessage(UserData userData) {
        String lineSeparator = System.lineSeparator();
        return notificationText(userData) +
                lineSeparator + lineSeparator +
                summaryDataText.getMessage(userData);
    }

    private String notificationText(UserData userData) {
        return localMessageSource.getLocaleMessage(SUMMARY_NOTIFICATION_CODE, userData.getUsername());
    }
}
