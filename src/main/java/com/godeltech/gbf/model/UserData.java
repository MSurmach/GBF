package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.LinkedList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Long id;
    private Long telegramId;
    private String username;
    private int deliverySize;
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

    //possibility to delete

    private RoutePoint tempRoutePoint;
    private LinkedList<State> stateHistory = new LinkedList<>();
    private LinkedList<String> callbackHistory = new LinkedList<>();
    private Page<TelegramUser> page;
    private int pageNumber;
    private TelegramUser tempForSearch;

    public UserData(Long telegramId, String username) {
        this.telegramId = telegramId;
        this.username = username;
    }

    public boolean isEmpty() {
        return route.isEmpty() &&
                startDate == null &&
                comment == null &&
                deliverySize == 0 &&
                seats == 0;
    }
}
