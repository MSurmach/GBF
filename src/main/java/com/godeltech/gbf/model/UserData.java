package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Country;
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
    private LinkedList<Integer> cityIds = new LinkedList<>(); //possibility to delete
    private LinkedList<RoutePoint> routePoints = new LinkedList<>();
    private LocalDate startDate;
    private LocalDate endDate;

    //possibility to delete

    private RoutePoint tempRoutePoint;
    private LinkedList<State> stateHistory = new LinkedList<>();
    private LinkedList<String> callbackHistory = new LinkedList<>();
    private Page<TelegramUser> page;
    private int pageNumber;
    private Role role;
    private String callbackQueryId;
    private TelegramUser tempForSearch;
    private Country tempCountry;

    public UserData(Long telegramId, String username) {
        this.telegramId = telegramId;
        this.username = username;
    }
}
