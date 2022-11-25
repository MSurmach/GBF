package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.model.db.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {
    Optional<UserStatistic> findUserStatisticByTelegramUser(TelegramUser telegramUser);

    @Query("SELECT SUM(statistic.registrationCount) FROM UserStatistic statistic")
    Optional<Long> totalRegistrationSum();

    @Query("SELECT SUM(statistic.requestCount) FROM UserStatistic statistic")
    Optional<Long> totalRequestSum();

    List<UserStatistic> findTop5ByOrderByRegistrationCountDesc();
    List<UserStatistic> findTop5ByOrderByRequestCountDesc();
}
