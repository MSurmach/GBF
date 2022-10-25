package com.godeltech.gbf.cache;

import com.godeltech.gbf.model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDataCache {

    private final static Map<Long, UserData> USERDATA_CACHE = new HashMap<>();

    public static UserData get(Long telegramUserId) {
        return USERDATA_CACHE.get(telegramUserId);
    }

    public static void initializeByIdAndUsername(Long telegramUserId, String username) {
        UserData created = new UserData(telegramUserId, username);
        USERDATA_CACHE.put(telegramUserId, created);
    }

    public static void add(long userId, UserData userData) {
        USERDATA_CACHE.put(userId, userData);
    }
}
