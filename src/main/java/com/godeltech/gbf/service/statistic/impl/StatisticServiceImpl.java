package com.godeltech.gbf.service.statistic.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Statistic;
import com.godeltech.gbf.model.StatisticLeader;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.session_cache.SessionCacheService;
import com.godeltech.gbf.service.statistic.StatisticService;
import com.godeltech.gbf.service.user.TelegramUserService;
import com.godeltech.gbf.service.user_statistic.UserStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final TelegramUserService telegramUserService;
    private final UserStatisticService userStatisticService;
    private final SessionCacheService sessionCacheService;
    private final OfferService offerService;

    @Override
    public Statistic getStatistic() {
        return Statistic.builder().
                allUsersCount(telegramUserService.getUserCount()).
                allSessionCount(sessionCacheService.getSessionCount()).
                activeRegistrationsCount(offerService.countByRole(Role.COURIER)).
                allRegistrationsInHistoryCount(userStatisticService.totalRegistrationSum()).
                registrationLeaders(
                        userStatisticService.getLeaders(Role.COURIER).stream().
                                map(userStatistic ->
                                        StatisticLeader.builder().
                                                telegramUser(userStatistic.getTelegramUser()).
                                                allOffersCount(userStatistic.getRegistrationCount()).
                                                activeOffersCount(
                                                        offerService.findAllOffersByUserIdAndRole(
                                                                userStatistic.getTelegramUser().getId(), Role.COURIER, 0).getTotalElements()).
                                                build()).toList()).
                activeRequestsCount(offerService.countByRole(Role.CLIENT)).
                allRequestsInHistoryCount(userStatisticService.totalRequestSum()).
                requestLeaders(userStatisticService.getLeaders(Role.CLIENT).stream().
                        map(userStatistic ->
                                StatisticLeader.builder().
                                        telegramUser(userStatistic.getTelegramUser()).
                                        allOffersCount(userStatistic.getRequestCount()).
                                        activeOffersCount(
                                                offerService.findAllOffersByUserIdAndRole(
                                                        userStatistic.getTelegramUser().getId(), Role.CLIENT, 0).getTotalElements()).
                                        build()).toList()).
                build();
    }
}
