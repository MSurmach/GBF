package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.TelegramUser;

public interface TelegramUserService {
    TelegramUser getOrCreateUser(Long id, String username);

    TelegramUser findById(Long id);

}
