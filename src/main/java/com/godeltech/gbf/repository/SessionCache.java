package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.TelegramUser;

import java.util.Optional;

public interface SessionCache {
    Session save(TelegramUser telegramUser);
    Optional<Session> get (TelegramUser telegramUser);
    Optional<Session> get (Long telegramId);
    int count();
}
