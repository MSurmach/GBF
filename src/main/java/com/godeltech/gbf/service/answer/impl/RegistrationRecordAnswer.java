package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationRecordAnswer implements Answer {

    private ConfirmationAnswer confirmationAnswer;

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        return confirmationAnswer.confirmationDataText(userData);
    }
}
