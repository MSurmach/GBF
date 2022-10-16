package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.DecimalMax;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    public Page<TelegramUser> findTelegramUsersBySearchDataAndRole(TelegramUser searchData, Role role, int pageNumber);

    Page<TelegramUser> findUsersByTelegramIdAndRole(Long telegramUserId, Role role, int pageNumber);

    void removeByExpiredAtBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);

    void deleteById(Long id);

    void save(UserData userData);
}
