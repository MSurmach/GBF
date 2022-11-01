package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;

public class ModelUtils {

    public static Offer createOffer(SessionData sessionData) {
        Offer offer = Offer.builder().
                startDate(sessionData.getStartDate()).
                endDate(sessionData.getEndDate()).
                routePoints(sessionData.getRoute()).
                comment(sessionData.getComment()).
                seats(sessionData.getSeats()).
                role(sessionData.getRole()).
                delivery(sessionData.getDelivery()).
                build();
        sessionData.getRoute().forEach(routePoint -> routePoint.setOffer(offer));
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

    public static SessionData createUserDataFromTelegramUser(TelegramUser telegramUser) {
        return null;
//        return UserData.builder().
//                id(telegramUser.getId()).
//                telegramId(telegramUser.getTelegramId()).
//                username(telegramUser.getUsername()).
//                documentsExist(telegramUser.isDocumentsExist()).
//                packageSize(telegramUser.getPackageSize()).
//                companionCount(telegramUser.getCompanionCount()).
//                comment(telegramUser.getComment()).
//                routePoints(new LinkedList<>(telegramUser.getRoutePoints())).
//                role(telegramUser.getRole()).
//                build();
    }

    public static void copyData(SessionData to, TelegramUser from) {
//        to.setId(from.getId());
//        to.setTelegramId(from.getTelegramId());
//        to.setUsername(from.getUsername());
//        to.setDocumentsExist(from.isDocumentsExist());
//        to.setDeliverySize(from.getPackageSize());
//        to.setSeats(from.getCompanionCount());
//        to.setComment(from.getComment());
//        to.setRoutePoints(new LinkedList<>(from.getRoutePoints()));
//        to.setRole(from.getRole());
    }
}
