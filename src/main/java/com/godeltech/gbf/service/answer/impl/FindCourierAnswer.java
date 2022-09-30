package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindCourierAnswer implements Answer {
    private LocalMessageSource localMessageSource;
    public final static String COURIERS_COUNT_CODE = "couriers.count";
    public final static String COURIERS_NOT_FOUND_CODE = "couriers.notfound";
    public final static String COURIERS_FOUND_CODE = "couriers.found";

    @Override
    public String getAnswer(UserData userData) {
        List<UserData> foundUsers = userData.getFoundUsers();
        int count = foundUsers.size();
        String messageCode = count == 0 ? COURIERS_NOT_FOUND_CODE : COURIERS_FOUND_CODE;

        return localMessageSource.getLocaleMessage(COURIERS_COUNT_CODE, String.valueOf(count)) +
                System.lineSeparator() +
                localMessageSource.getLocaleMessage(messageCode);
    }
}
