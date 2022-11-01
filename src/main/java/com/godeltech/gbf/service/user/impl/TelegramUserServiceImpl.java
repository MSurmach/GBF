package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {
    private TelegramUserRepository telegramUserRepository;

    @Override
    public Optional<TelegramUser> getById(Long id) {
        return telegramUserRepository.findById(id);
    }

    @Override
    public TelegramUser save(UserData userData) {
        TelegramUser telegramUser = TelegramUser.builder().
                id(userData.getTelegramId()).
                userName(userData.getUsername()).
                build();
        return telegramUserRepository.save(telegramUser);
    }
}
