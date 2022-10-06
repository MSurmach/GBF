package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<UserRecord> findByUserDataAndRole(UserData userData, Role role);

    Page<UserRecord> findByTelegramUserIdAndRole(Long telegramUserId, Role role, int pageNumber);

    UserRecord findByTelegramUserIdAndRecordId(Long telegramUserId, Long recordId);

    void removeByDateToBefore(LocalDate date);

    void removeByChangedAtAfter(LocalDate date);

    void deleteById(Long id);

    void save(UserData userData);
}
