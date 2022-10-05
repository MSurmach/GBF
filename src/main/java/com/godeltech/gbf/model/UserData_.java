package com.godeltech.gbf.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(UserData.class)
public abstract class UserData_ {
    public static SingularAttribute<UserData, Long> telegramUserId;
    public static SingularAttribute<UserData, String> username;
    public static SingularAttribute<UserData, String> countryFrom;
    public static SingularAttribute<UserData, String> countryTo;
    public static SingularAttribute<UserData, String> cityFrom;
    public static SingularAttribute<UserData, String> cityTo;
    public static SingularAttribute<UserData, LocalDate> dateTo;
    public static SingularAttribute<UserData, LocalDate> dateFrom;
    public static SingularAttribute<UserData, Boolean> documents;
    public static SingularAttribute<UserData, String> packageSize;
    public static SingularAttribute<UserData, Integer> companionCount;
    public static SingularAttribute<UserData, String> comment;
}
