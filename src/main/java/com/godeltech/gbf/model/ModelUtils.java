package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;

import java.util.LinkedList;
import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.LINK_TO_USER_PATTERN;
import static com.godeltech.gbf.gui.utils.ConstantUtil.MENTION_TO_USER_PATTERN;

public class ModelUtils {

    public static Offer mapSessionDataToOffer(Session session) {
        Offer offer = Offer.builder().
                id(session.getOfferId()).
                startDate(session.getStartDate()).
                endDate(session.getEndDate()).
                routePoints(new LinkedList<>()).
                comment(session.getComment()).
                seats(session.getSeats()).
                role(session.getRole()).
                delivery(session.getDelivery()).
                telegramUser(session.getTelegramUser()).
                build();
        session.getRoute().forEach(offer::addRoutePoint);
        return offer;
    }

    public static void resetSessionData(Session session) {
        session.setOfferId(null);
        session.setDelivery(null);
        session.setSeats(0);
        session.setRole(null);
        session.setRoute(new LinkedList<>());
        session.setStartDate(null);
        session.setEndDate(null);
        session.setComment(null);
        session.setStateHistory(new LinkedList<>());
        session.setCallbackHistory(new LinkedList<>());
        session.setCallbackQueryId(null);
        session.setSearchOffer(null);
        session.setPageNumber(0);
        session.setOffers(null);
        session.setEditable(false);
        session.setTempOfferId(null);
    }

    public static Session mapOfferToSessionData(Offer offer) {
        return Session.builder().
                offerId(offer.getId()).
                telegramUser(offer.getTelegramUser()).
                seats(offer.getSeats()).
                delivery(offer.getDelivery()).
                route(new LinkedList<>(offer.getRoutePoints())).
                comment(offer.getComment()).
                role(offer.getRole()).
                startDate(offer.getStartDate()).
                endDate(offer.getEndDate()).
                build();
    }

    public static void copyOfferToSessionData(Session to, Offer from) {
        to.setOfferId(from.getId());
        to.setTelegramUser(from.getTelegramUser());
        to.setSeats(from.getSeats());
        to.setDelivery(from.getDelivery());
        to.setComment(from.getComment());
        to.setRoute(new LinkedList<>(from.getRoutePoints()));
        to.setRole(from.getRole());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
    }

    public static String getUserMention(Session session) {
        return adviceUserMention(session.getTelegramUser().getUserName(), session.getTelegramUser().getId());
    }

    public static String getUserMention(Offer offer) {
        return adviceUserMention(offer.getTelegramUser().getUserName(), offer.getTelegramUser().getId());
    }

    public static String getUserMention(TelegramUser telegramUser) {
        return adviceUserMention(telegramUser.getUserName(), telegramUser.getId());
    }

    private static String adviceUserMention(String username, Long telegramUserId) {
        return Objects.isNull(username) ?
                String.format(LINK_TO_USER_PATTERN, telegramUserId, username) :
                String.format(MENTION_TO_USER_PATTERN, username);
    }
}