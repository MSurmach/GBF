package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    List<UserData> findUserDataByTelegramUserId(Long telegramUserId);

    UserData findUserDataByTelegramUserIdAndId(Long telegramUserId, Long recordId);

    long removeUserDataByDateToBefore(LocalDate date);

    List<UserData> findByCityFromAndCityToAndDateFromContainsAndDateToContains(String cityFrom,
                                                                               String cityTo,
                                                                               LocalDate dateFrom,
                                                                               LocalDate dateTo);
}
