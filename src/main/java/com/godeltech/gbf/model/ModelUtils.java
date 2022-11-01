package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;

public class ModelUtils {

    public static Offer createOffer(UserData userData) {
        TelegramUser telegramUser = TelegramUser.builder().
                id(userData.getTelegramId()).
                userName(userData.getUsername()).
                build();
        return Offer.builder().
                telegramUser(telegramUser).
                startDate(userData.getStartDate()).
                endDate(userData.getEndDate()).
                routePoints(userData.getRoute()).
                comment(userData.getComment()).
                seats(userData.getSeats()).
                role(userData.getRole()).
                delivery(userData.getDelivery()).
                build();
    }

    public static TelegramUser telegramUser(UserData userData) {
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

    public static void resetUserData(UserData userData) {
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

    public static UserData createUserDataFromTelegramUser(TelegramUser telegramUser) {
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

    public static void copyData(UserData to, TelegramUser from) {
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
