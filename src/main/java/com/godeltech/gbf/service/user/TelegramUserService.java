package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;

import java.util.Optional;

public interface TelegramUserService {
    Optional<TelegramUser> getById(Long id);
    TelegramUser save(UserData userData);
}
