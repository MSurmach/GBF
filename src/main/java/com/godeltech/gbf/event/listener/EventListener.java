package com.godeltech.gbf.event.listener;

import com.godeltech.gbf.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransaction(NotificationEvent notificationEvent){
        log.error("Got notification event ={}",notificationEvent);
        log.error("Got notification event ={}",notificationEvent);
        log.error("Got notification event ={}",notificationEvent);
        log.error("Got notification event ={}",notificationEvent);
    }
}
