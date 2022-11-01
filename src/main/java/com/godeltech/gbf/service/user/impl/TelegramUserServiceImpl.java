package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TelegramUserServiceImpl implements TelegramUserService {
    private TelegramUserRepository telegramUserRepository;

    @Override
    @Transactional
    public TelegramUser getOrCreateUser(Long id, String username) {
        return telegramUserRepository.findById(id)
                .orElse(telegramUserRepository.save(TelegramUser.builder()
                        .id(id)
                        .userName(username)
                        .build()));
    }

    @Override
    @Transactional
    public TelegramUser save(UserData userData) {
        TelegramUser telegramUser = TelegramUser.builder().
                id(userData.getTelegramId()).
                userName(userData.getUsername()).
                build();
        return telegramUserRepository.save(telegramUser);
    }
}
