package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.impl.CouriersListText;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CouriersListStateView implements StateView<SendMessage> {
    private CouriersListText couriersListText;


    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        List<UserData> foundCouriers = userData.getFoundCouriers();
        List<SendMessage> views = new ArrayList<>();
        views.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(couriersListText.initialMessage()).
                replyMarkup(null).
                build());
        for (UserData courier : foundCouriers) {
            SendMessage sendMessage = SendMessage.builder().
                    chatId(chatId).
                    parseMode("html").
                    text(couriersListText.getText(courier)).
                    replyMarkup(null).
                    build();
            views.add(sendMessage);
        }
        return views;
    }
}
