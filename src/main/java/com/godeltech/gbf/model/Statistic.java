package com.godeltech.gbf.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Statistic {
    //Common
    private long allUsersCount;
    private int allSessionCount;
    //Courier registrations
    private Long activeRegistrationsCount;
    private Long allRegistrationsInHistoryCount;
    private List<StatisticLeader> registrationLeaders;
    //Client requests
    private Long activeRequestsCount;
    private Long allRequestsInHistoryCount;
    private List<StatisticLeader> requestLeaders;
}
