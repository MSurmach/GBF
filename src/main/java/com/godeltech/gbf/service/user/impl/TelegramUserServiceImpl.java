package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.exception.ResourceNotFoundException;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TelegramUserServiceImpl implements TelegramUserService {
    private TelegramUserRepository telegramUserRepository;

    @Override
    @Transactional
    public TelegramUser getOrCreateUser(Long id, String username) {
        log.info("Get or create telegram user by id : {} and username : {}",id,username);
        return telegramUserRepository.findById(id)
                .orElse(telegramUserRepository.save(TelegramUser.builder()
                        .id(id)
                        .userName(username)
                        .build()));
    }

    @Override
    @Transactional
    public TelegramUser findById(Long id) {
        log.info("Get telegram user by id : {}",id);
        return telegramUserRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(TelegramUser.class,"id",id));
    }
}
