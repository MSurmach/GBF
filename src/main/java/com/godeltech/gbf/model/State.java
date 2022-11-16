package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum State {
    BACK, THANKS_FOR_FEEDBACK,
    FORM,
    MENU, SUCCESS_REGISTRATION, WRONG_INPUT,
    COMMENT,
    ROUTE,
    YEAR, MONTH, DATE,
    DELIVERY, SEATS,
    MY_OFFERS, ALL_OFFERS, OFFER_BY_ID_NOT_FOUND,
    SEARCH_RESULT,
    NON_USABLE, OFFER_ID_INPUT,
    FEEDBACK, ALL_FEEDBACKS,
    FEEDBACK_DELETE_BY_ID
}
