package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentQuizText implements Text {
    public final static String COMMENT_QUESTION_CODE = "comment.question";
    private LocalMessageSource localMessageSource;

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(COMMENT_QUESTION_CODE);
    }
}
