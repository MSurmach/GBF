package com.godeltech.gbf.service.session_cache.impl;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.SessionCache;
import com.godeltech.gbf.service.session_cache.SessionCacheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionCacheServiceImpl implements SessionCacheService {
    private final SessionCache sessionCache;

    public Session get(TelegramUser telegramUser){
        Optional<Session> optionalSessionData = sessionCache.get(telegramUser);
        Session session;
        if (optionalSessionData.isPresent()){
            session = optionalSessionData.get();
            if (!Objects.equals(session.getTelegramUser(), telegramUser)) {
                session.setTelegramUser(telegramUser);
            }
        }
        else {
            session = sessionCache.save(telegramUser);
            session.getStateHistory().push(State.MENU);
        }
        return session;
    }

    @Override
    public int getSessionCount() {
        return sessionCache.count();
    }
}
