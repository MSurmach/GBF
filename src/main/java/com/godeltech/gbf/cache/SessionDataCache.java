package com.godeltech.gbf.cache;

import com.godeltech.gbf.model.SessionData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDataCache {

    private final static Map<Long, SessionData> SESSION_DATA_CACHE = new ConcurrentHashMap<>();

    public static SessionData get(Long telegramUserId) {
        return SESSION_DATA_CACHE.get(telegramUserId);
    }

    public static void initializeByIdAndUsernameAndFirstNameAndLastName(Long telegramUserId, String username, String firstName, String lastName) {
        SessionData created = new SessionData(telegramUserId, username, firstName, lastName);
        SESSION_DATA_CACHE.put(telegramUserId, created);
    }

    public static void add(long userId, SessionData sessionData) {
        SESSION_DATA_CACHE.put(userId, sessionData);
    }
}
