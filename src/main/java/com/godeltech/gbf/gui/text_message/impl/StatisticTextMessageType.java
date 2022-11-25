package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Statistic;
import com.godeltech.gbf.service.statistic.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.STATISTIC_CONTENT_CODE;

@Component
@AllArgsConstructor
public class StatisticTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;
    private final StatisticService statisticService;
    @Override
    public State getState() {
        return State.STATISTIC;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        Statistic statistic = statisticService.getStatistic();
        return lms.getLocaleMessage(STATISTIC_CONTENT_CODE, ModelUtils.statisticAsArrayArgs(statistic));
    }
}
