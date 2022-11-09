package com.godeltech.gbf.service.notification.impl;

import com.godeltech.gbf.GbfBot;
import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.event.NotificationEvent;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.notification.NotificationService;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

import static com.godeltech.gbf.gui.utils.ConstantUtil.CLIENT_NOTIFICATION;
import static com.godeltech.gbf.gui.utils.ConstantUtil.COURIER_NOTIFICATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final OfferService offerService;
    private final GbfBot gbfBot;
    private final LocalMessageSource localMessageSource;

    @Override
    public void makeNotifications(NotificationEvent notificationEvent) {
        Offer savedOffer = (Offer) notificationEvent.getPayload();
        log.info("Make notification by saved offer : {} ", savedOffer);
        List<Offer> offers =offerService.findSuitableOffersListByGivenOffer(savedOffer);
        if (!offers.isEmpty()) {
            String textMessage = savedOffer.getRole() == Role.COURIER ?
                    localMessageSource.getLocaleMessage(CLIENT_NOTIFICATION) :
                    localMessageSource.getLocaleMessage(COURIER_NOTIFICATION);
            offers.forEach(offer -> sendNotificationMessageToUser(offer,textMessage));
        }
    }

    private void sendNotificationMessageToUser(Offer offer, String textMessage) {
        log.info("Send notification message for user with offer id : {}", offer.getTelegramUser().getId());
        try {
            gbfBot.execute(SendMessage.builder()
                    .chatId(offer.getTelegramUser().getId().toString())
                    .text(textMessage+offer.getId())
                    .build());
        } catch (TelegramApiException e) {
            log.error("");
        }
    }
}
