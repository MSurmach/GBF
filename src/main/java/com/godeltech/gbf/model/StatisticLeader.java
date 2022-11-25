package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.TelegramUser;
import lombok.Builder;

@Builder
public class StatisticLeader {
    private TelegramUser telegramUser;
    private int allOffersCount;
    private long activeOffersCount;

    @Override
    public String toString() {
        return String.format("%s / %d / %d", ModelUtils.getUserMention(telegramUser), allOffersCount, activeOffersCount);
    }
}
