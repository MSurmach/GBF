package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;

import java.util.LinkedList;

public class ModelUtils {

    public static Offer mapSessionDataToOffer(SessionData sessionData) {
        Offer offer = Offer.builder().
                id(sessionData.getOfferId()).
                startDate(sessionData.getStartDate()).
                endDate(sessionData.getEndDate()).
                routePoints(new LinkedList<>()).
                comment(sessionData.getComment()).
                seats(sessionData.getSeats()).
                role(sessionData.getRole()).
                delivery(sessionData.getDelivery()).
                telegramUser(TelegramUser.builder()
                        .id(sessionData.getTelegramUserId())
                        .userName(sessionData.getUsername())
                        .firstName(sessionData.getFirstName())
                        .lastName(sessionData.getLastName())
                        .build()).
                build();
        sessionData.getRoute().forEach(offer::addRoutePoint);
        return offer;
    }

    public static void resetSessionData(SessionData sessionData) {
        sessionData.setOfferId(null);
        sessionData.setDelivery(null);
        sessionData.setSeats(0);
        sessionData.setRole(null);
        sessionData.setRoute(new LinkedList<>());
        sessionData.setStartDate(null);
        sessionData.setEndDate(null);
        sessionData.setComment(null);
        sessionData.setStateHistory(new LinkedList<>());
        sessionData.setCallbackHistory(new LinkedList<>());
        sessionData.setCallbackQueryId(null);
        sessionData.setSearchOffer(null);
        sessionData.setPageNumber(0);
        sessionData.setOffers(null);
        sessionData.setEditable(false);
        sessionData.setTempOfferIdSearch(null);
    }

    public static SessionData mapOfferToSessionData(Offer offer) {
        return SessionData.builder().
                offerId(offer.getId()).
                telegramUserId(offer.getTelegramUser().getId()).
                username(offer.getTelegramUser().getUserName()).
                seats(offer.getSeats()).
                delivery(offer.getDelivery()).
                route(new LinkedList<>(offer.getRoutePoints())).
                comment(offer.getComment()).
                role(offer.getRole()).
                firstName(offer.getTelegramUser().getFirstName()).
                lastName(offer.getTelegramUser().getLastName()).
                startDate(offer.getStartDate()).
                endDate(offer.getEndDate()).
                build();
    }

    public static void copyOfferToSessionData(SessionData to, Offer from) {
        to.setOfferId(from.getId());
        to.setTelegramUserId(from.getTelegramUser().getId());
        to.setUsername(from.getTelegramUser().getUserName());
        to.setFirstName(from.getTelegramUser().getFirstName());
        to.setLastName(from.getTelegramUser().getLastName());
        to.setSeats(from.getSeats());
        to.setDelivery(from.getDelivery());
        to.setComment(from.getComment());
        to.setRoute(new LinkedList<>(from.getRoutePoints()));
        to.setRole(from.getRole());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
    }
}