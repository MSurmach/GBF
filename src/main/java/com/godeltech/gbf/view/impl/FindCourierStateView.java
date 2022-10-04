package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.repository.specification.UserDataSpecs;
import com.godeltech.gbf.service.answer.impl.FindCourierAnswer;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class FindCourierStateView implements StateView<SendMessage> {
    private UserDataRepository userDataRepository;
    private FindCourierAnswer answer;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        try {
//            List<UserData> foundUsers = userDataRepository.findByCityFromAndCityToAndDateFromContainsAndDateToContains(
//                    userData.getCityFrom(),
//                    userData.getCityTo(),
//                    userData.getDateFrom(),
//                    userData.getDateTo()
//            );
            //List<UserData> foundUsers = userDataRepository.findByCityFromAndCityTo(userData.getCityFrom(), userData.getCityTo());
            List<UserData> foundUsers = userDataRepository.findAll(UserDataSpecs.byCityFrom(userData.getCityFrom()));
            userData.setFoundUsers(foundUsers);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        SendMessage sendMessage = SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(answer.getAnswer(userData)).
                replyMarkup(null).build();
        return List.of(sendMessage);
    }
}
