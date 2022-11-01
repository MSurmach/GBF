package com.godeltech.gbf.cache;

import com.godeltech.gbf.model.SessionData;

import java.util.HashMap;
import java.util.Map;

public class UserDataCache {

    private final static Map<Long, SessionData> USERDATA_CACHE = new HashMap<>();

    public static SessionData get(Long telegramUserId) {
        return USERDATA_CACHE.get(telegramUserId);
    }

    public static void initializeByIdAndUsername(Long telegramUserId, String username) {
        SessionData created = new SessionData(telegramUserId, username);
        USERDATA_CACHE.put(telegramUserId, created);
    }

    public static void add(long userId, SessionData sessionData) {
        USERDATA_CACHE.put(userId, sessionData);
    }
}
