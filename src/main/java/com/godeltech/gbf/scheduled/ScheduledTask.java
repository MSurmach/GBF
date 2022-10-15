package com.godeltech.gbf.scheduled;

import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ScheduledTask {

    private UserService userService;

    @Scheduled(cron = "@midnight")
    @Transactional
    public void removeExpiredRecords() {
        LocalDate localDateNow = LocalDate.now();
        userService.removeByExpiredAtBefore(localDateNow);
    }

    @Scheduled(cron = "@midnight")
    @Transactional
    public void removeRequestAfter5Days() {
        LocalDate after5Days = LocalDate.now().plusDays(5);
        userService.removeByChangedAtAfter(after5Days);
    }
}
