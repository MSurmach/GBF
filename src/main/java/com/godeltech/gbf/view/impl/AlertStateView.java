package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.List;

@Service
@AllArgsConstructor
public class AlertStateView implements StateView<AnswerCallbackQuery> {

    private LocalMessageSource localMessageSource;

    public final static String ALERT_CONTENT_CODE = "alert.content";

    @Override
    public List<AnswerCallbackQuery> buildView(Long chatId, UserData userData) {
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(userData.getCallbackQueryId()).
                showAlert(true).
                text(localMessageSource.getLocaleMessage(ALERT_CONTENT_CODE)).
                cacheTime(60).
                build();
        return List.of(answerCallbackQuery);
    }
}
