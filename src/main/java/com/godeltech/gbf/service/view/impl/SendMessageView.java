package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardTypeFactory;
import com.godeltech.gbf.factory.impl.TextMessageTypeFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.feedback.FeedbackService;
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

    private final TextMessageTypeFactory textMessageTypeFactory;
    private final KeyboardTypeFactory keyboardTypeFactory;
    private final OfferService offerService;
    private final FeedbackService feedbackService;

    private final List<State> statesNeededForOffersPageInit = List.of(MY_OFFERS, SEARCH_RESULT, ALL_OFFERS);

    public SendMessage buildView(Long chatId, Session session) {
        State state = session.getStateHistory().peek();
        if (statesNeededForOffersPageInit.contains(state)) initOffersPage(session);
        if (Objects.equals(state, ALL_FEEDBACKS))
            session.setFeedbacks(feedbackService.findAllFeedbacks());
        if (Objects.equals(state,NOTIFICATION))
            session.setProposedOffer(offerService.findOfferById(session.getProposedOfferId()));
        TextMessageType textMessageType = textMessageTypeFactory.get(state);
        KeyboardType keyboardType = keyboardTypeFactory.get(state);
        return SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(textMessageType.getMessage(session)).
                replyMarkup(keyboardType.getKeyboardMarkup(session)).build();
    }

    private void initOffersPage(Session session) {
        State state = session.getStateHistory().peek();
        Page<Offer> offers = switch (state) {
            case MY_OFFERS -> offerService.findAllOffersByUserIdAndRole(
                    session.getTelegramUser().getId(),
                    revertRoleForOfferSearching(session.getRole()),
                    session.getPageNumber());
            case ALL_OFFERS -> offerService.findAllByRole(
                    revertRoleForOfferSearching(session.getRole()),
                    session.getPageNumber());
            default ->
                    offerService.findSuitableOffersByGivenOffer(session.getSearchOffer(), session.getPageNumber());
        };
        session.setOffers(offers);
    }

    private Role revertRoleForOfferSearching(Role role) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ? Role.COURIER : Role.CLIENT;
    }
}
