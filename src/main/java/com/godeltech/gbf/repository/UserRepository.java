package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<TelegramUser, Long>, JpaSpecificationExecutor<TelegramUser> {

    Page<TelegramUser> findByTelegramIdAndRole(Long telegramId, Role role, Pageable pageable);

    TelegramUser findByTelegramIdAndId(Long telegramId, Long id);

    void removeByExpiredAtBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);
}
