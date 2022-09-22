package com.godeltech.gbf.cache;

import com.godeltech.gbf.model.UserData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class UserDataCache {

    private final static Map<Long, UserData> USERDATA_CACHE = new HashMap<>();
    private final static Map<Long, LinkedList<String>> СALLBACK_CACHE = new HashMap<>();

    public static void add(Long userId, UserData userData) {
        USERDATA_CACHE.put(userId, userData);
    }

    public static UserData get(Long userId) {
        return USERDATA_CACHE.get(userId);
    }

    public static void delete(Long userId) {
        USERDATA_CACHE.remove(userId);
    }

    public static void saveCallback(long userId, String callback) {
        LinkedList<String> callbackSequence = СALLBACK_CACHE.computeIfAbsent(userId, k -> new LinkedList<>());
        callbackSequence.push(callback);
    }

    public static String getPreviousCallBack(long userId) {
        return СALLBACK_CACHE.get(userId).pop();
    }
}
