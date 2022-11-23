package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.*;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Long offerId;
    private TelegramUser telegramUser;
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
    private Page<Offer> offers;
    private List<Feedback> feedbacks;
    private int pageNumber;
    private Offer searchOffer;
    private boolean isEditable;
    private Long tempOfferId;

    public void clearTemp() {
        tempStartDate = null;
        tempEndDate = null;
        tempRoute = new LinkedList<>();
    }

    public Session(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
    }

    public boolean isEmpty() {
        return route.isEmpty() &&
                startDate == null &&
                comment == null &&
                delivery == null &&
                seats == 0;
    }
}
