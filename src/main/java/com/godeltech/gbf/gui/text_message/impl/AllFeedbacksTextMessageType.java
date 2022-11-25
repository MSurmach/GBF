package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Feedback;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_CONTENT_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_NOT_FOUND_CODE;

@Component
@AllArgsConstructor
public class AllFeedbacksTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;
    private final TelegramUserService telegramUserService;

    @Override
    public State getState() {
        return State.ALL_FEEDBACKS;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        List<Feedback> feedbacks = session.getFeedbacks();
        if (feedbacks == null || feedbacks.isEmpty()) return lms.getLocaleMessage(FEEDBACK_NOT_FOUND_CODE);
        StringBuilder feedBackMessageBuilder = new StringBuilder();
        for (Feedback feedBack : feedbacks) {
            TelegramUser feedbacker = telegramUserService.findById(feedBack.getUserId()).get();
            String feedBackMessage = lms.getLocaleMessage(FEEDBACK_CONTENT_CODE, ModelUtils.getUserMention(feedbacker), feedBack.getUserId().toString(), feedBack.getId().toString(), feedBack.getContent());
            feedBackMessageBuilder.append(feedBackMessage).
                    append(System.lineSeparator());
        }
        return feedBackMessageBuilder.toString();
    }
}
