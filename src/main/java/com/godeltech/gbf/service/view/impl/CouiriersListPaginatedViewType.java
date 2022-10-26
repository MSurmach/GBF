package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.impl.SuccessRegistrationKeyboardType;
import com.godeltech.gbf.gui.keyboard.impl.PaginationKeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.service.view.ViewType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.godeltech.gbf.model.State.COURIERS_LIST_RESULT;


@Component
public class CouiriersListPaginatedViewType extends PaginatedView implements ViewType<SendMessage> {

    public CouiriersListPaginatedViewType(UserService userService, PaginationKeyboardType paginationKeyboard,
                                          SuccessRegistrationKeyboardType backMenuKeyboard, MessageFactory messageFactory,
                                          KeyboardFactory keyboardFactory) {
        super(userService, paginationKeyboard, backMenuKeyboard, messageFactory, keyboardFactory);
    }

    @Override
    public State getState() {
        return COURIERS_LIST_RESULT;
    }
}
