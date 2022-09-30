package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FindCourierStateView implements StateView<SendMessage> {
    private UserDataRepository userDataRepository;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        try {
            List<UserData> foundUsers = userDataRepository.findByCityFromAndCityToAndDateFromContainsAndDateToContains(
                    userData.getCityFrom(),
                    userData.getCityTo(),
                    userData.getDateFrom(),
                    userData.getDateTo()
            );
            userData.setFoundUsers(foundUsers);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
