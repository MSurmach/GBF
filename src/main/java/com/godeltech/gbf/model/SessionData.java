package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.LinkedList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionData {
    private Long offerId;
    private Long telegramUserId;
    private String firstName;
    private String lastName;
    private String username;
    private Delivery delivery;
    private int seats;
    private String comment;
    private LinkedList<RoutePoint> tempRoute = new LinkedList<>();
    private LinkedList<RoutePoint> route = new LinkedList<>();
    private LocalDate tempStartDate;
    private LocalDate startDate;
    private LocalDate tempEndDate;
    private LocalDate endDate;
    private Role role;
    private String callbackQueryId;

    private LinkedList<State> stateHistory = new LinkedList<>();

    private LinkedList<String> callbackHistory = new LinkedList<>();

    private Page<Offer> page;

    private int pageNumber;

    private Offer searchOffer;

    public void clearTemp() {
        tempStartDate = null;
        tempEndDate = null;
        tempRoute = new LinkedList<>();
    }

    public SessionData(Long telegramUserId, String username, String firstName, String lastName) {
        this.telegramUserId = telegramUserId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean isEmpty() {
        return route.isEmpty() &&
                startDate == null &&
                comment == null &&
                delivery == null &&
                seats == 0;
    }
}
