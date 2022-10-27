package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;

import java.time.LocalDate;
import java.util.LinkedList;

public class ModelUtils {
    public static TelegramUser telegramUser(UserData userData) {
//        LinkedList<RoutePoint> routePoints = userData.getRoutePoints();
//        routePoints.getLast().setOrderNumber(routePoints.size() - 1);
//        LocalDate nowTime = LocalDate.now();
//        LocalDate expiredAtDate = userData.getRoutePoints().getLast().getVisitDate();
//        TelegramUser telegramUser = TelegramUser.builder().
//                id(userData.getId()).
//                username(userData.getUsername()).
//                documentsExist(userData.isDocumentsExist()).
//                packageSize(userData.getPackageSize()).
//                companionCount(userData.getCompanionCount()).
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
        userData.setId(null);
        userData.setDocumentsExist(false);
        userData.setPackageSize(0);
        userData.setCompanionCount(0);
        userData.setComment(null);
        userData.setRoutePoints(new LinkedList<>());
        userData.setTempRoutePoint(null);
        userData.setStateHistory(new LinkedList<>());
        userData.setCallbackHistory(new LinkedList<>());
        userData.setPage(null);
        userData.setPageNumber(0);
        userData.setRole(null);
        userData.setCallbackQueryId(null);
        userData.setTempForSearch(null);
        userData.setTempCountry(null);
    }

    public static UserData createUserDataFromTelegramUser(TelegramUser telegramUser) {
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
        return null;
    }

    public static void copyData(UserData to, TelegramUser from) {
//        to.setId(from.getId());
//        to.setTelegramId(from.getTelegramId());
//        to.setUsername(from.getUsername());
//        to.setDocumentsExist(from.isDocumentsExist());
//        to.setPackageSize(from.getPackageSize());
//        to.setCompanionCount(from.getCompanionCount());
//        to.setComment(from.getComment());
//        to.setRoutePoints(new LinkedList<>(from.getRoutePoints()));
//        to.setRole(from.getRole());
    }
}
