package com.godeltech.gbf.service.session_cache;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.TelegramUser;

public interface SessionCacheService {
    Session get(TelegramUser telegramUser);

    Session get(Long telegramId);

    int getSessionCount();
}
