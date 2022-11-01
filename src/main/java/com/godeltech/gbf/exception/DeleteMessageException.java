package com.godeltech.gbf.exception;

import com.godeltech.gbf.model.db.BotMessage;
import lombok.Getter;

import static com.godeltech.gbf.utils.ConstantUtils.ExceptionMessages.CANNOT_DELETE_MESSAGE_EXCEPTION_PATTERN;

@Getter
public class DeleteMessageException extends RuntimeException {
    private BotMessage botMessage;

    public DeleteMessageException(BotMessage botMessage) {
        super(String.format(CANNOT_DELETE_MESSAGE_EXCEPTION_PATTERN,
                botMessage.getMessageId(), botMessage.getChatId()));
        this.botMessage = botMessage;
    }
}
