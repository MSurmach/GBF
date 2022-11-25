package com.godeltech.gbf.exception;

import com.godeltech.gbf.model.db.TelegramUser;

public class NotFoundStateTypeException extends RuntimeException {
    public NotFoundStateTypeException(Class<?> classType, TelegramUser telegramUser) {
        super(String.format("State wasn't found in class : %s by user : %s",classType, telegramUser));
    }
}
