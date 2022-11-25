package com.godeltech.gbf.service.user_statistic;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.model.db.UserStatistic;

import java.util.List;

public interface UserStatisticService {

    void collectStatistic(TelegramUser telegramUser, Role role);
    long totalRegistrationSum();
    long totalRequestSum();

    List<UserStatistic> getLeaders(Role role);
}
