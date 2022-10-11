package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditorMessage implements Message {
    private LocalMessageSource localMessageSource;
    private SummaryDataMessage summaryDataText;

    public final static String REGISTRATION_EDITOR_NOTE_CODE = "registration.editor.note";

    @Override
    public String getMessage(UserData userData) {
        String mainNote = localMessageSource.getLocaleMessage(REGISTRATION_EDITOR_NOTE_CODE);
        String editableData = summaryDataText.getMessage(userData);
        return mainNote + System.lineSeparator() + editableData;
    }
}
