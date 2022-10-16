package com.godeltech.gbf.service.route_point.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.RoutePointRepository;
import com.godeltech.gbf.service.route_point.RoutePointService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.godeltech.gbf.repository.specification.RoutePointSpecs.*;

@Service
@AllArgsConstructor
public class RoutePointServiceImpl implements RoutePointService {

    private RoutePointRepository routePointRepository;

    @Override
    public List<RoutePoint> findCourierRoutePointsByRoutePoints(List<RoutePoint> searchRoutePoints) {
        Role role = Role.COURIER;
        List<RoutePoint> results = new LinkedList<>();
        for (RoutePoint searchRoutePoint : searchRoutePoints) {
            Specification<RoutePoint> searchSpecification = byRole(role);
            searchSpecification = searchSpecification.and(byCountry(searchRoutePoint.getCountry()));
            City city = searchRoutePoint.getCity();
            if (city != null) searchSpecification = searchSpecification.and(byCityEquals(city));
            LocalDate visitDate = searchRoutePoint.getVisitDate();
            if (visitDate != null) searchSpecification = searchSpecification.and(byVisitDate(visitDate));
            results.addAll(routePointRepository.findAll(searchSpecification));
        }
        return results;
    }

    @Override
    public List<RoutePoint> findClientRoutePointsByRoutePoints(List<RoutePoint> searchRoutePoints) {
        Role role = Role.CLIENT;
        List<RoutePoint> results = new LinkedList<>();
        for (RoutePoint searchRoutePoint : searchRoutePoints) {
            Specification<RoutePoint> searchSpecification =
                    byRole(role).
                            and(byCountry(searchRoutePoint.getCountry())).
                            and(byCityEquals(searchRoutePoint.getCity()).or(byCityIsNull())).
                            and(byVisitDate(searchRoutePoint.getVisitDate()).or(byVisitDateIsNull()));
            results.addAll(routePointRepository.findAll(searchSpecification));
        }
        return results;
    }
}
