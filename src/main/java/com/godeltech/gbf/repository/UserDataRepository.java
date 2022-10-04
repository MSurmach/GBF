package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData, Long>, JpaSpecificationExecutor<UserData> {

    List<UserData> findUserDataByTelegramUserId(Long telegramUserId);

    UserData findUserDataByTelegramUserIdAndId(Long telegramUserId, Long recordId);

    void removeUserDataByDateToBefore(LocalDate date);
}
