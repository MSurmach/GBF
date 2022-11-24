package com.godeltech.gbf.repository.impl;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.SessionCache;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SessionCacheImpl implements SessionCache {
    private final static Map<Long, Session> SESSION_CACHE = new ConcurrentHashMap<>();

    @Override
    public Session save(TelegramUser telegramUser) {
        Session newSession = new Session(telegramUser);
        SESSION_CACHE.put(telegramUser.getId(), newSession);
        return newSession;
    }

    @Override
    public Optional<Session> get(TelegramUser telegramUser) {
        return Optional.ofNullable(SESSION_CACHE.get(telegramUser.getId()));
    }

    @Override
    public int count() {
        return SESSION_CACHE.size();
    }
}
