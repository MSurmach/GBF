package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TelegramUserServiceImpl implements TelegramUserService {
    private TelegramUserRepository telegramUserRepository;

    @Override
    @Transactional
    public TelegramUser saveUser(TelegramUser telegramUser) {
        log.info("Get or create telegram user by id : {} and username : {}", telegramUser.getId(), telegramUser.getUserName());
        return telegramUserRepository.save(telegramUser);
    }

    @Override
    public Optional<TelegramUser> findById(Long id) {
        log.info("Get telegram user by id : {}", id);
        return telegramUserRepository.findById(id);
    }

    @Override
    public long getUserCount() {
        log.info("Get count of all telegram users");
        return telegramUserRepository.count();
    }


}
