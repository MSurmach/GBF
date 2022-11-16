package com.godeltech.gbf.event.listener;

import com.godeltech.gbf.event.NotificationEvent;
import com.godeltech.gbf.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync(proxyTargetClass = true)
public class EventListener implements AsyncConfigurer {

    private final NotificationService notificationService;

    @Async("threadPoolTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotificationEvent(NotificationEvent notificationEvent) {
        log.info("Got notification event ={}", notificationEvent);
        notificationService.makeNotifications(notificationEvent);
    }
}
