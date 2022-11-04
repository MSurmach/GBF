package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static com.godeltech.gbf.model.Role.CLIENT;
import static com.godeltech.gbf.model.State.REGISTRATIONS;
import static com.godeltech.gbf.model.State.REQUESTS;


@AllArgsConstructor
public abstract class PaginatedView {
    private OfferService offerService;
    private MessageFactory messageFactory;
    private KeyboardFactory keyboardFactory;


    public List<SendMessage> buildView(Long chatId, SessionData sessionData) {
        long telegramId = sessionData.getTelegramUserId();
        State currentState = sessionData.getStateHistory().peek();
        Page<Offer> page = switch (sessionData.getRole()) {
            case REGISTRATIONS_VIEWER -> {
                if (currentState == REGISTRATIONS)
                    yield offerService.findAllOffersByUserIdAndRole(telegramId, Role.COURIER, sessionData.getPageNumber());
                else
                    yield offerService.findSuitableOffersByGivenOffer(sessionData.getSearchOffer(), sessionData.getPageNumber());
            }
            case REQUESTS_VIEWER -> {
                if (currentState == REQUESTS)
                    yield offerService.findAllOffersByUserIdAndRole(telegramId, CLIENT, sessionData.getPageNumber());
                else
                    yield offerService.findSuitableOffersByGivenOffer(sessionData.getSearchOffer(), sessionData.getPageNumber());
            }
            case CLIENT ->
                    offerService.findSuitableOffersByGivenOffer(sessionData.getSearchOffer(), sessionData.getPageNumber());
            default -> null;
        };
        sessionData.setPage(page);
        SendMessage sendMessage = SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).getMessage(sessionData)).
                replyMarkup(keyboardFactory.get(currentState).getKeyboardMarkup(sessionData)).
                build();
        return List.of(sendMessage);
    }
}
