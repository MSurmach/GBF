package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.answer.impl.FindCourierAnswer;
import com.godeltech.gbf.service.user.UserDataService;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class FindCourierStateView implements StateView<SendMessage> {
    private UserDataService userDataService;
    private FindCourierAnswer answer;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        List<UserData> foundUsers = userDataService.findByUserData(userData);
        userData.setFoundUsers(foundUsers);
        SendMessage sendMessage = SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(answer.getAnswer(userData)).
                replyMarkup(null).build();
        return List.of(sendMessage);
    }
}
