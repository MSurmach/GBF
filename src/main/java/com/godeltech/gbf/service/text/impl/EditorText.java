package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditorText implements Text {
    private LocalMessageSource localMessageSource;
    private SummaryDataText summaryDataText;

    public final static String REGISTRATION_EDITOR_NOTE_CODE = "registration.editor.note";

    @Override
    public String getText(UserData userData) {
        String mainNote = localMessageSource.getLocaleMessage(REGISTRATION_EDITOR_NOTE_CODE);
        String editableData = summaryDataText.getText(userData);
        return mainNote + System.lineSeparator() + editableData;
    }
}
