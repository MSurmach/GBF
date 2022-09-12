package com.godeltech.gbf.cache;

import com.godeltech.gbf.model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDataCache {

    private final static Map<Long, UserData> USERDATA_CACHE = new HashMap<>();

    public static void addToCache(Long userId, UserData userData) {
        USERDATA_CACHE.put(userId, userData);
    }

    public static UserData getUserDataFromCache(Long userId) {
        return USERDATA_CACHE.get(userId);
    }

}
