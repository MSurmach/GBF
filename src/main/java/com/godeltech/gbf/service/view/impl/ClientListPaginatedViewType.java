package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.impl.SuccessRegistrationKeyboardType;
import com.godeltech.gbf.gui.keyboard.impl.PaginationKeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.user.TelegramUserService;
import com.godeltech.gbf.service.view.ViewType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.godeltech.gbf.model.State.CLIENTS_LIST_RESULT;


@Component
public class ClientListPaginatedViewType extends PaginatedView implements ViewType<SendMessage> {

    public ClientListPaginatedViewType(OfferService offerService,
                                       PaginationKeyboardType paginationKeyboard,
                                       MessageFactory messageFactory,
                                       KeyboardFactory keyboardFactory,
                                       LocalMessageSource lms) {
        super(offerService, paginationKeyboard, messageFactory, keyboardFactory, lms);
    }

    @Override
    public State getState() {
        return CLIENTS_LIST_RESULT;
    }
}
