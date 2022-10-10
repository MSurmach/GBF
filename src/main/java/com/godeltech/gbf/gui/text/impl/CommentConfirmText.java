package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentConfirmText implements Text {
    public final static String COMMENT_CONFIRMATION_CODE = "comment.confirmation";
    private LocalMessageSource localMessageSource;

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(COMMENT_CONFIRMATION_CODE, userData.getUsername(), userData.getComment());
    }
}
