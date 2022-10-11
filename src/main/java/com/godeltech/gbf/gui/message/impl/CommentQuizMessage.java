package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentQuizMessage implements Message {
    public final static String COMMENT_QUESTION_CODE = "comment.question";
    private final LocalMessageSource localMessageSource;

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(COMMENT_QUESTION_CODE);
    }
}
