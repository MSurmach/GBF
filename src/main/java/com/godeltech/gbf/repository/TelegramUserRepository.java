package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long>, JpaSpecificationExecutor<TelegramUser> {
    Page<TelegramUser> findUsersByTelegramIdAndRole(Long telegramId, Role role, Pageable pageable);

    TelegramUser findUserByTelegramIdAndId(Long telegramId, Long id);

    void removeByExpiredAtBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);
}
