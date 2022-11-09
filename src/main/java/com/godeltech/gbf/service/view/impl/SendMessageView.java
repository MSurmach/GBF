package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.view.View;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class SendMessageView implements View<SendMessage> {

    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;
    private final OfferService offerService;

    private final List<State> preInitStates = List.of(MY_OFFERS, COURIERS_SEARCH_RESULT, CLIENTS_SEARCH_RESULT, ALL_OFFERS);

    public SendMessage buildView(Long chatId, SessionData sessionData) {
        State state = sessionData.getStateHistory().peek();
        if (preInitStates.contains(state)) preInitializeSearchResults(sessionData);
        MessageType messageType = messageFactory.get(state);
        KeyboardType keyboardType = keyboardFactory.get(state);
        return SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(messageType.getMessage(sessionData)).
                replyMarkup(keyboardType.getKeyboardMarkup(sessionData)).build();
    }

    private void preInitializeSearchResults(SessionData sessionData) {
        State state = sessionData.getStateHistory().peek();
        Page<Offer> page = switch (state) {
            case MY_OFFERS -> offerService.findAllOffersByUserIdAndRole(
                    sessionData.getTelegramUserId(),
                    revertRoleForSearching(sessionData.getRole()),
                    sessionData.getPageNumber());
            case ALL_OFFERS -> offerService.findAllByRole(
                    revertRoleForSearching(sessionData.getRole()),
                    sessionData.getPageNumber());
            default ->
                    offerService.findSuitableOffersByGivenOffer(sessionData.getSearchOffer(), sessionData.getPageNumber());
        };
        sessionData.setOffers(page);
    }

    private Role revertRoleForSearching(Role role) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ? Role.COURIER : Role.CLIENT;
    }
}
