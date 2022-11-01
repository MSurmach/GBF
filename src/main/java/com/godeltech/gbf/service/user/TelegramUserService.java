package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;

public interface TelegramUserService {
    TelegramUser getOrCreateUser(Long id, String username);
    TelegramUser save(UserData userData);
}
