package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<UserRecord> findByUserDataAndRole(UserData userData, Role role);

    List<UserRecord> findByTelegramUserId(Long telegramUserId);

    UserRecord findByTelegramUserIdAndRecordId(Long telegramUserId, Long recordId);

    void removeByDateToBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);
    void deleteById(Long id);

    void save(UserData userData);
}
