package com.godeltech.gbf.model;

import com.godeltech.gbf.model.db.RoutePoint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.LinkedList;

@Data
@NoArgsConstructor
public class UserData {
    private Long userId;
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
    private Page<UserRecord> recordsPage;
    private int pageNumber;
    private Role role;
    private String callbackQueryId;
    private UserData tempForSearch;

    public UserData(Long telegramId, String username) {
        this.telegramId = telegramId;
        this.username = username;
    }
}
