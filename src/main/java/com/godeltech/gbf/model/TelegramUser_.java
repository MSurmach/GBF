package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.util.List;

@StaticMetamodel(TelegramUser.class)
public abstract class TelegramUser_ {
    public static SingularAttribute<TelegramUser, Long> id;
    public static SingularAttribute<TelegramUser, Long> telegramId;
    public static SingularAttribute<TelegramUser, String> username;
    public static SingularAttribute<TelegramUser, LocalDate> changedAt;
    public static SingularAttribute<TelegramUser, LocalDate> expiredAt;
    public static SingularAttribute<TelegramUser, Boolean> documents;
    public static SingularAttribute<TelegramUser, String> packageSize;
    public static SingularAttribute<TelegramUser, Integer> companionCount;
    public static SingularAttribute<TelegramUser, String> comment;
    public static SingularAttribute<TelegramUser, Role> role;
    public static SingularAttribute<TelegramUser, List<RoutePoint>> routePoints;
}
