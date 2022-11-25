package com.godeltech.gbf.service.user_statistic.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.model.db.UserStatistic;
import com.godeltech.gbf.repository.UserStatisticRepository;
import com.godeltech.gbf.service.user_statistic.UserStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.model.Role.COURIER;

@Service
@AllArgsConstructor
public class UserStatisticServiceImpl implements UserStatisticService {
    private final UserStatisticRepository userStatisticRepository;

    @Override
    public void collectStatistic(TelegramUser telegramUser, Role role) {
        userStatisticRepository.findUserStatisticByTelegramUser(telegramUser).ifPresentOrElse(
                userStatistic -> userStatisticRepository.save(increaseCounter(userStatistic, role)),
                () -> {
                    UserStatistic created = UserStatistic.builder().
                            telegramUser(telegramUser).
                            build();
                    userStatisticRepository.save(increaseCounter(created, role));
                }
        );
    }

    @Override
    public long totalRegistrationSum() {
        return userStatisticRepository.totalRegistrationSum().orElse(0L);

    }

    @Override
    public long totalRequestSum() {
        return userStatisticRepository.totalRequestSum().orElse(0L);
    }

    @Override
    public List<UserStatistic> getLeaders(Role role) {
        if (Objects.equals(role, COURIER))
            return userStatisticRepository.findTop5ByOrderByRegistrationCountDesc();
        else return userStatisticRepository.findTop5ByOrderByRequestCountDesc();
    }

    private UserStatistic increaseCounter(UserStatistic userStatistic, Role role) {
        switch (role) {
            case COURIER, REGISTRATIONS_VIEWER ->
                    userStatistic.setRegistrationCount(userStatistic.getRegistrationCount() + 1);
            case CLIENT, REQUESTS_VIEWER -> userStatistic.setRequestCount(userStatistic.getRequestCount() + 1);
        }
        return userStatistic;
    }
}
