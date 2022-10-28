package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.service.validator.OfferValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.FORM;

@Service
@AllArgsConstructor
public class FormHandlerType implements HandlerType {

    private UserService userService;
    private OfferValidator offerValidator;

    @Override
    public State getState() {
        return FORM;
    }

    @Override
    public State handle(UserData userData) {
        var callback = userData.getCallbackHistory().peek();
        var clicked = FormButton.valueOf(callback);
        switch (clicked) {
            case FORM_CONFIRM_AS_COURIER, FORM_CONFIRM_AS_CLIENT, FORM_CONFIRM_AS_REGISTRATION_VIEWER -> {
                offerValidator.validateOfferBeforeSave(userData);
                userService.save(userData);
            }
        }
        return clicked.getNextState();
    }

//    private State saveAndGetAppropriateState(UserData userData) {
//        State nextState = switch (userData.getRole()) {
//            case COURIER -> SUCCESS_REGISTRATION;
//            case CLIENT -> {
//                TelegramUser telegramUser = ModelUtils.telegramUser(userData);
//                userData.setTempForSearch(telegramUser);
//                yield COURIERS_LIST_RESULT;
//            }
//            case REGISTRATIONS_VIEWER -> REGISTRATIONS;
//            case REQUESTS_VIEWER -> REQUESTS;
//        };
//        userService.save(userData);
//        return nextState;
//    }
}
