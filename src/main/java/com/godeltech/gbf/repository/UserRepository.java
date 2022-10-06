package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<UserRecord, Long>, JpaSpecificationExecutor<UserRecord> {

    Page<UserRecord> findByTelegramUserIdAndRole(Long telegramUserId, Role role, Pageable pageable);

    UserRecord findByTelegramUserIdAndRecordId(Long telegramUserId, Long recordId);

    void removeByDateToBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);
}
