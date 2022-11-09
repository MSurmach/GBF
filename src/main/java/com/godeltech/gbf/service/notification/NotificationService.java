package com.godeltech.gbf.service.notification;

import com.godeltech.gbf.event.NotificationEvent;

public interface NotificationService {
    void makeNotifications(NotificationEvent notificationEvent);
}
