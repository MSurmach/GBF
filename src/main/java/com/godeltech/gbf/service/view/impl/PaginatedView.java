package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.impl.PaginationKeyboardType;
import com.godeltech.gbf.gui.utils.KeyboardUtils;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.model.Role.CLIENT;
import static com.godeltech.gbf.model.Role.COURIER;
import static com.godeltech.gbf.model.State.REGISTRATIONS;
import static com.godeltech.gbf.model.State.REQUESTS;


@AllArgsConstructor
public abstract class PaginatedView  {
    private OfferService offerService;
    private PaginationKeyboardType paginationKeyboard;
    private MessageFactory messageFactory;
    private KeyboardFactory keyboardFactory;
    private LocalMessageSource lms;


    public List<SendMessage> buildView(Long chatId, SessionData sessionData) {
        long telegramId = sessionData.getTelegramUserId();
        State currentState = sessionData.getStateHistory().peek();
        Page<Offer> page = switch (sessionData.getRole()) {
            case REGISTRATIONS_VIEWER -> {
                if (currentState == REGISTRATIONS)
                    yield offerService.findAllOffersByUserIdAndRole(telegramId, Role.COURIER, sessionData.getPageNumber());
                else
                    yield offerService.findAllOffersBySessionDataAndRole(sessionData, CLIENT, sessionData.getPageNumber());
            }
            case REQUESTS_VIEWER -> {
                if (currentState == REQUESTS)
                    yield offerService.findAllOffersByUserIdAndRole(telegramId, CLIENT, sessionData.getPageNumber());
                else
                    yield offerService.findAllOffersBySessionDataAndRole(sessionData, COURIER, sessionData.getPageNumber());
            }
            case CLIENT ->
                    offerService.findAllOffersBySessionDataAndRole(sessionData, COURIER, sessionData.getPageNumber());
            default -> null;
        };
        sessionData.setPage(page);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (page != null && !page.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(sessionData) :
                KeyboardUtils.backAndMenuMarkup(lms);
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).initialMessage(sessionData)).
                replyMarkup(keyboardMarkup).
                build());
        if (page != null && !page.isEmpty()) {
            for (Offer offer : page) {
                SessionData fromDb = ModelUtils.mapOfferToSessionData(offer);
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(messageFactory.get(currentState).getMessage(fromDb)).
                        replyMarkup(keyboardFactory.get(currentState).getKeyboardMarkup(fromDb)).
                        build();
                messages.add(sendMessage);
            }
        }
        return messages;
    }
}
