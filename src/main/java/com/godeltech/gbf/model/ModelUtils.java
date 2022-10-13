package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;

import java.time.LocalDate;
import java.util.LinkedList;

public class ModelUtils {
    public static TelegramUser telegramUser(UserData userData) {
        return TelegramUser.builder().
                telegramId(userData.getTelegramId()).
                username(userData.getUsername()).
                documentsExist(userData.isDocumentsExist()).
                packageSize(userData.getPackageSize()).
                companionCount(userData.getCompanionCount()).
                comment(userData.getComment()).
                routePoints(userData.getRoutePoints()).
                role(userData.getRole()).
                changedAt(LocalDate.now()).
                expiredAt(userData.getRoutePoints().getLast().getVisitDate()).
                build();
    }

    public static void resetUserData(UserData userData) {
        userData.setUserId(null);
        userData.setTelegramId(null);
        userData.setUsername(null);
        userData.setDocumentsExist(false);
        userData.setPackageSize(null);
        userData.setCompanionCount(0);
        userData.setComment(null);
        userData.setRoutePoints(new LinkedList<>());
        userData.setTempRoutePoint(null);
        userData.setStateHistory(new LinkedList<>());
        userData.setCallbackHistory(new LinkedList<>());
        userData.setRecordsPage(null);
        userData.setPageNumber(0);
        userData.setRole(null);
        userData.setCallbackQueryId(null);
        userData.setTempForSearch(null);
    }

    public static void copyFromDBtoUserData(TelegramUser telegramUser, UserData userData) {
        userData.setUserId(telegramUser.getUserId());
        userData.setTelegramId(telegramUser.getTelegramId());
        userData.setUsername(telegramUser.getUsername());
        userData.setDocumentsExist(telegramUser.isDocumentsExist());
        userData.setPackageSize(telegramUser.getPackageSize());
        userData.setCompanionCount(telegramUser.getCompanionCount());
        userData.setComment(telegramUser.getComment());
        userData.setRoutePoints((LinkedList<RoutePoint>) telegramUser.getRoutePoints());
        userData.setRole(telegramUser.getRole());
    }
}
