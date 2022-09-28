package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentQuizAnswer implements Answer {
    public final static String COMMENT_QUESTION_CODE = "comment.question";
    private LocalMessageSource localMessageSource;

    @Override
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(COMMENT_QUESTION_CODE);
    }
}
