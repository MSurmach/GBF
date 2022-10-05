package com.godeltech.gbf.scheduled;

import com.godeltech.gbf.service.user.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ScheduledTask {

    private UserDataService userDataService;

    @Scheduled(cron = "@midnight")
    @Transactional
    public void removeExpiredRecords() {
        LocalDate localDateNow = LocalDate.now();
        userDataService.removeUserDataByDateToBefore(localDateNow);
    }
}
