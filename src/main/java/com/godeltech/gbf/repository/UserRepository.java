package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<UserRecord, Long>, JpaSpecificationExecutor<UserRecord> {

    List<UserRecord> findByTelegramUserId(Long telegramUserId);

    UserRecord findByTelegramUserIdAndRecordId(Long telegramUserId, Long recordId);

    void removeByDateToBefore(LocalDate date);

    void removeByChangedAtAfter (LocalDate date);
}
