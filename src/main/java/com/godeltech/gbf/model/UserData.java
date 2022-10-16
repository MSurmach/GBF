package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Long id;
    private Long telegramId;
    private String username;
    private boolean documentsExist;
    private String packageSize;
    private int companionCount;
    private String comment;

    private LinkedList<RoutePoint> routePoints = new LinkedList<>();
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

    public boolean isEmpty() {
        return this.routePoints.isEmpty() &&
                Objects.isNull(this.comment) &&
                Objects.isNull(this.packageSize) &&
                !this.documentsExist &&
                this.companionCount == 0;
    }
}
