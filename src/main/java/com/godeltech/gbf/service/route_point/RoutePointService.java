package com.godeltech.gbf.service.route_point;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.RoutePoint;

import java.util.List;

public interface RoutePointService {
    List<RoutePoint> findCourierRoutePointsByRoutePoints(List<RoutePoint> clientRoutePoints);
    List<RoutePoint> findClientRoutePointsByRoutePoints(List<RoutePoint> searchRoutePoints);
}
