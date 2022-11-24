package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.db.TelegramUser;

import java.util.Optional;

public interface TelegramUserService {
    TelegramUser saveUser(TelegramUser telegramUser);

    Optional<TelegramUser> findById(Long id);

    long getUserCount();
}
