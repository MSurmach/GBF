package com.godeltech.gbf.cache;

import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.TelegramUser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDataCache {

    private final static Map<Long, SessionData> SESSION_DATA_CACHE = new ConcurrentHashMap<>();

    public static SessionData get(Long telegramUserId) {
        return SESSION_DATA_CACHE.get(telegramUserId);
    }

    public static void initializeSession(TelegramUser telegramUser) {
        SessionData newSession = new SessionData(telegramUser.getId(), telegramUser.getUserName(), telegramUser.getFirstName(), telegramUser.getLastName(), telegramUser.getLanguage());
        SESSION_DATA_CACHE.put(telegramUser.getId(), newSession);
    }

    public static void add(long userId, SessionData sessionData) {
        SESSION_DATA_CACHE.put(userId, sessionData);
    }
}
