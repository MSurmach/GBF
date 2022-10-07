package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface UserService {
    Page<UserRecord> findCourierByUserDataAndRole(UserData userData, Role role, int pageNumber);

    Page<UserRecord> findClientByUserDataAndRole(UserData userData, Role role, int pageNumber);
    Page<UserRecord> findByTelegramUserIdAndRole(Long telegramUserId, Role role, int pageNumber);

    UserRecord findByTelegramUserIdAndRecordId(Long telegramUserId, Long recordId);

    void removeByDateToBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);

    void deleteById(Long id);

    void save(UserData userData);
}
