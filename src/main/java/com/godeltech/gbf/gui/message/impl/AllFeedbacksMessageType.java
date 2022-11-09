package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Feedback;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_CONTENT_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_NOT_FOUND_CODE;

@Component
@AllArgsConstructor
public class AllFeedbacksMessageType implements MessageType {
    private final LocalMessageSource lms;
    private final TelegramUserService telegramUserService;

    @Override
    public State getState() {
        return State.ALL_FEEDBACKS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Page<Feedback> feedbacks = sessionData.getFeedbacks();
        if (feedbacks == null || feedbacks.isEmpty()) return lms.getLocaleMessage(FEEDBACK_NOT_FOUND_CODE);
        StringBuilder feedBackMessageBuilder = new StringBuilder();
        for (Feedback feedBack : feedbacks) {
            String username = telegramUserService.findById(feedBack.getUserId()).getUserName();
            String feedBackMessage = lms.getLocaleMessage(FEEDBACK_CONTENT_CODE, username, feedBack.getUserId().toString(), feedBack.getContent());
            feedBackMessageBuilder.append(feedBackMessage).
                    append(System.lineSeparator());
        }
        return feedBackMessageBuilder.toString();
    }
}
