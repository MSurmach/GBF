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
                build();
        sessionData.getRoute().forEach(offer::addRoutePoint);
        return offer;
    }

    public static TelegramUser telegramUser(SessionData sessionData) {
//        LinkedList<RoutePoint> routePoints = userData.getRoutePoints();
//        routePoints.getLast().setOrderNumber(routePoints.size() - 1);
//        LocalDate nowTime = LocalDate.now();
//        LocalDate expiredAtDate = userData.getRoutePoints().getLast().getVisitDate();
//        TelegramUser telegramUser = TelegramUser.builder().
//                id(userData.getId()).
//                telegramId(userData.getTelegramId()).
//                username(userData.getUsername()).
//                documentsExist(userData.isDocumentsExist()).
//                packageSize(userData.getDeliverySize()).
//                companionCount(userData.getSeats()).
//                comment(userData.getComment()).
//                routePoints(new LinkedList<>()).
//                role(userData.getRole()).
//                changedAt(nowTime).
//                expiredAt(expiredAtDate).
//                build();
//        routePoints.forEach(telegramUser::addRoutePoint);
//        return telegramUser;
        return null;
    }

    public static void resetUserData(SessionData sessionData) {
//        userData.setId(null);
//        userData.setDocumentsExist(false);
//        userData.setDeliverySize(0);
//        userData.setSeats(0);
//        userData.setComment(null);
//        userData.setRoutePoints(new LinkedList<>());
//        userData.setTempRoutePoint(null);
//        userData.setStateHistory(new LinkedList<>());
//        userData.setCallbackHistory(new LinkedList<>());
//        userData.setPage(null);
//        userData.setPageNumber(0);
//        userData.setRole(null);
//        userData.setCallbackQueryId(null);
//        userData.setTempForSearch(null);
//        userData.setTempCountry(null);
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
        to.setDelivery(from.getDelivery());;
        to.setComment(from.getComment());
        to.setRoute(new LinkedList<>(from.getRoutePoints()));
        to.setRole(from.getRole());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
    }
}