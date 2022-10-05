package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationRecordText implements Text {

    private SummaryDataText summaryDataText;

    @Override
    public String getText(UserData userData) {
        return summaryDataText.getText(userData);
    }
}
