package com.godeltech.gbf.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMessageCache {
    private final static Map<Long, List<Integer>> USER_MESSAGE_CACHE = new HashMap<>();

    public static void cacheUserIdAndMessageId(Long telegramUserId, Integer messageId) {
        if (USER_MESSAGE_CACHE.containsKey(telegramUserId)) {
            USER_MESSAGE_CACHE.get(telegramUserId).add(messageId);
        } else {
            List<Integer> messageIdsList = new ArrayList<>();
            messageIdsList.add(messageId);
            USER_MESSAGE_CACHE.put(telegramUserId, messageIdsList);
        }
    }

    public static List<Integer> getMessageIds(Long telegramUserId) {
        if (USER_MESSAGE_CACHE.containsKey(telegramUserId)) {
            List<Integer> messageIds = USER_MESSAGE_CACHE.get(telegramUserId);
            USER_MESSAGE_CACHE.remove(telegramUserId);
            return messageIds;
        }
        return null;
    }
}
