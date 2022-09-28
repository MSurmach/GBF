package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationEditorAnswer implements Answer {
    private LocalMessageSource localMessageSource;
    private ConfirmationAnswer confirmationAnswer;

    public final static String REGISTRATION_EDITOR_NOTE_CODE = "registration.editor.note";

    @Override
    public String getAnswer(UserData userData) {
        String mainNote = localMessageSource.getLocaleMessage(REGISTRATION_EDITOR_NOTE_CODE);
        String editableData = confirmationAnswer.confirmationDataText(userData);
        return mainNote + System.lineSeparator() + editableData;
    }
}
