package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentAnswer implements Answer {
    public final static String COMMENT_CODE = "comment";
    private LocalMessageSource localMessageSource;

    @Override
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(COMMENT_CODE);
    }
}
