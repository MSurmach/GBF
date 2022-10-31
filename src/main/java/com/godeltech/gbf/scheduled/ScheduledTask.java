package com.godeltech.gbf.scheduled;

import com.godeltech.gbf.GbfBot;
import com.godeltech.gbf.model.db.BotMessage;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final BotMessageService botMessageService;
    private final OfferService offerService;
    private final GbfBot gbfBot;

    @Scheduled(cron = "@midnight")
    @Transactional
    public void removeExpiredMessages() {
        LocalDateTime expiredDate = LocalDateTime.now().plusHours(7);
        log.info("Remove expired message whose date is after : {}", expiredDate);
        List<BotMessage> expiredMessages = botMessageService.findExpiredMessageByDate(expiredDate);
        expiredMessages.forEach(botMessage -> {
            botMessageService.delete(botMessage);
            gbfBot.deleteExpiredMessage(botMessage);
        });
    }

    @Scheduled(fixedDelayString ="${schedule.work}")
    @Transactional
    public void removeExpiredOffers() {
        LocalDate expiredDate = LocalDate.now();
        log.info("Remove expired offer by date : {}", expiredDate);
        offerService.deleteExpiredOffersByDate(expiredDate);
    }
}
