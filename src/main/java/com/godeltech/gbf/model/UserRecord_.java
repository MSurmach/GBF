package com.godeltech.gbf.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(UserRecord.class)
public abstract class UserRecord_ {
    public static SingularAttribute<UserRecord, Long> telegramUserId;
    public static SingularAttribute<UserRecord, String> username;
    public static SingularAttribute<UserRecord, String> countryFrom;
    public static SingularAttribute<UserRecord, String> countryTo;
    public static SingularAttribute<UserRecord, String> cityFrom;
    public static SingularAttribute<UserRecord, String> cityTo;
    public static SingularAttribute<UserRecord, LocalDate> dateTo;
    public static SingularAttribute<UserRecord, LocalDate> dateFrom;
    public static SingularAttribute<UserRecord, LocalDate> changedAt;
    public static SingularAttribute<UserRecord, Boolean> documents;
    public static SingularAttribute<UserRecord, String> packageSize;
    public static SingularAttribute<UserRecord, Integer> companionCount;
    public static SingularAttribute<UserRecord, String> comment;
    public static SingularAttribute<UserRecord, Role> role;
}
