package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SummaryConfirmationText implements Text {
    public final static String SUMMARY_NOTIFICATION_CODE = "summary.notification";
    private final LocalMessageSource localMessageSource;
    private final SummaryDataText summaryDataText;

    @Override
    public String getText(UserData userData) {
        String lineSeparator = System.lineSeparator();
        return notificationText(userData) +
                lineSeparator + lineSeparator +
                summaryDataText.getText(userData);
    }

    private String notificationText(UserData userData) {
        return localMessageSource.getLocaleMessage(SUMMARY_NOTIFICATION_CODE, userData.getUsername());
    }
}
