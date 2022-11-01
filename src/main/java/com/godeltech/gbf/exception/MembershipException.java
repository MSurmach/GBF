package com.godeltech.gbf.exception;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

import static com.godeltech.gbf.utils.ConstantUtils.ExceptionMessages.MEMBERSHIP_PATTERN_EXCEPTION;

@Getter
public class MembershipException extends RuntimeException {

    private Message botMessage;

    public MembershipException(Message botMessage) {
        super(String.format(MEMBERSHIP_PATTERN_EXCEPTION, botMessage.getFrom().getId(), botMessage.getFrom().getUserName()));
        this.botMessage = botMessage;
    }
}
