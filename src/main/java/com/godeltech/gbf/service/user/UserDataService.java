package com.godeltech.gbf.service.user;

import com.godeltech.gbf.model.UserData;

import java.time.LocalDate;
import java.util.List;

public interface UserDataService {
    List<UserData> findByUserData(UserData userData);

    List<UserData> findUserDataByTelegramUserId(Long telegramUserId);

    UserData findUserDataByTelegramUserIdAndId(Long telegramUserId, Long recordId);

    void removeUserDataByDateToBefore(LocalDate date);

    void deleteById(Long id);
}
