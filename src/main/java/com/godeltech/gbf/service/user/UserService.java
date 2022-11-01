package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface UserService {
    Page<TelegramUser> findTelegramUsersBySearchDataAndRole(TelegramUser searchData, Role role, int pageNumber);

    Page<TelegramUser> findUsersByTelegramIdAndRole(Long telegramUserId, Role role, int pageNumber);

    void removeByExpiredAtBefore(LocalDate date);

    void removeByChangedAtBefore(LocalDate date);

    void deleteById(Long id);

    void save(UserData userData);
}
