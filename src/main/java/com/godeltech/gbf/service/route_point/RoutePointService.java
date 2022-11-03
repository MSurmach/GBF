package com.godeltech.gbf.service.route_point;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.RoutePoint;
import liquibase.pro.packaged.R;

import java.util.List;

public interface RoutePointService {
//    List<RoutePoint> findRoutePointsByNeededRoutePointsAndByRoleAndNotEqualToTelegramId(List<RoutePoint> neededRoute,
//                                                                                        Role role,
//                                                                                        Long telegramId);

    List<Long> findOffersIdByRoutePoints(List<RoutePoint> routePoints);
}
