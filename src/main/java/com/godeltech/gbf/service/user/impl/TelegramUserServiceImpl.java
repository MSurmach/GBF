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
    public TelegramUser saveUser(Long id, String username, String language) {
        log.info("Get or create telegram user by id : {} and username : {}", id, username);
        return telegramUserRepository.save(TelegramUser.builder()
                .id(id)
                .userName(username)
                .language(language)
                .build());
    }

    @Override
    public Optional<TelegramUser> findById(Long id) {
        log.info("Get telegram user by id : {}", id);
        return telegramUserRepository.findById(id);
    }
}
