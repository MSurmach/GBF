package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
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
    private ControlKeyboard controlKeyboard;


    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        List<UserRecord> records = userData.getRecords();
        List<SendMessage> views = new ArrayList<>();
        views.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(couriersListText.initialMessage()).
                replyMarkup(null).
                build());
        for (UserRecord record : records) {
            UserData dataFromRecord = new UserData(record);
            SendMessage sendMessage = SendMessage.builder().
                    chatId(chatId).
                    parseMode("html").
                    text(couriersListText.getText(dataFromRecord)).
                    replyMarkup(null).
                    build();
            views.add(sendMessage);
        }
        views.add(
                SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(couriersListText.paginationInfoMessage(userData)).
                        replyMarkup(controlKeyboard.getKeyboardMarkup(userData)).
                        build());
        return views;
    }
}
