package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.db.TelegramUser;

import java.util.Optional;

public interface TelegramUserService {
    TelegramUser saveUser(Long id, String username, String language);

    Optional<TelegramUser> findById(Long id);

}
