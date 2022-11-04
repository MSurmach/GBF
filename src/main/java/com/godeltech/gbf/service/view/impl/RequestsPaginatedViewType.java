package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.view.ViewType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.godeltech.gbf.model.State.REQUESTS;


@Component
public class RequestsPaginatedViewType extends PaginatedView implements ViewType<SendMessage> {

    public RequestsPaginatedViewType(OfferService offerService,
                                     MessageFactory messageFactory,
                                     KeyboardFactory keyboardFactory) {
        super(offerService, messageFactory, keyboardFactory);
    }

    @Override
    public State getState() {
        return REQUESTS;
    }
}
