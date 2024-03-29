package com.godeltech.gbf.service.notification.impl;

import com.godeltech.gbf.service.GbfBot;
import com.godeltech.gbf.event.NotificationEvent;
import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.notification.NotificationService;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final OfferService offerService;
    private final GbfBot gbfBot;
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public void makeNotifications(NotificationEvent notificationEvent) {
        LocalMessageSource lms = localMessageSourceFactory.get(null);
        Offer savedOffer = (Offer) notificationEvent.getPayload();
        log.info("Make notification by saved offer : {} ", savedOffer);
        List<Offer> offers = offerService.findSuitableOffersListByGivenOffer(savedOffer);
        if (!offers.isEmpty()) {
            String notificationMessageCode = savedOffer.getRole() == Role.COURIER ?
                    CLIENT_NOTIFICATION :
                    COURIER_NOTIFICATION;
            offers.forEach(offer -> sendNotificationMessageToUser(savedOffer,offer, notificationMessageCode));
        }
    }

    private void sendNotificationMessageToUser(Offer savedOffer, Offer offer, String notificationMessageCode) {
        log.info("Send notification message for user with offer id : {}", offer.getTelegramUser().getId());
        LocalMessageSource lms = localMessageSourceFactory.get(offer.getTelegramUser().getLanguage());
        try {
            gbfBot.execute(SendMessage.builder()
                    .parseMode("html")
                    .chatId(offer.getTelegramUser().getId().toString())
                    .text(lms.getLocaleMessage(notificationMessageCode, offer.getId().toString()))
                            .replyMarkup(InlineKeyboardMarkup.builder()
                                    .keyboard(List.of(List.of(InlineKeyboardButton.builder()
                                                    .callbackData(State.NOTIFICATION+"&"+savedOffer.getId())
                                                    .text(lms.getLocaleMessage(NOTIFICATION_SHOW_CODE))
                                            .build())))
                                    .build())
                    .build());
        } catch (TelegramApiException e) {
            log.error("Message couldn't send to telegramuser with id : {} and about offer with id : {}",
                    offer.getTelegramUser().getId(), offer.getId());
        }
    }
}
