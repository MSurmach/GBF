package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum CommentBotButton implements BotButton {
    COMMENT_YES, COMMENT_NO, COMMENT_CONFIRM, COMMENT_EDIT;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
