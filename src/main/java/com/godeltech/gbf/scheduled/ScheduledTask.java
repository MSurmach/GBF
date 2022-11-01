package com.godeltech.gbf.scheduled;

import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ScheduledTask {

    private OfferService offerService;

    @Scheduled(cron = "@midnight")
    @Transactional
    public void removeExpiredRecords() {
        LocalDate localDateNow = LocalDate.now();
        offerService.removeByExpiredAtBefore(localDateNow);
    }

    @Scheduled(cron = "@midnight")
    @Transactional
    public void removeRequestAfter5Days() {
        LocalDate after5Days = LocalDate.now().minusDays(5);
        offerService.removeByChangedAtBefore(after5Days);
    }
}
