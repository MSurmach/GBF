package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationRecordAnswer implements Answer {

    private ConfirmationAnswer confirmationAnswer;

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        return confirmationAnswer.confirmationDataText(userData);
    }
}
