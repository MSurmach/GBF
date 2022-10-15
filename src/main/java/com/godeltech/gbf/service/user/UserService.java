package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface UserService {
    Page<TelegramUser> findCourierByUserDataAndRole(UserData userData, Role role, int pageNumber);

    Page<TelegramUser> findClientByUserDataAndRole(UserData userData, Role role, int pageNumber);
    Page<com.godeltech.gbf.model.db.TelegramUser> findUsersByTelegramIdAndRole(Long telegramUserId, Role role, int pageNumber);

    TelegramUser findByTelegramIdAndId(Long telegramId, Long id);

    void removeByExpiredAtBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);

    void deleteById(Long id);

    void save(UserData userData);
}
