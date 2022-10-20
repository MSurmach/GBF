package com.godeltech.gbf.service.route_point.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
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
            Specification<RoutePoint> searchSpecification = role(role);
            searchSpecification = searchSpecification.and(country(searchRoutePoint.getCountry()));
            City city = searchRoutePoint.getCity();
            if (city != null) searchSpecification = searchSpecification.and(cityEquals(city));
            LocalDate startDate = searchRoutePoint.getStartDate();
            LocalDate endDate = searchRoutePoint.getEndDate();
            if (startDate != null) {
                searchSpecification = searchSpecification.
                        and(startDateLessThanOrEquals(startDate).
                                and(endDateGreaterThanOrEquals(endDate)));
            }
//            LocalDate visitDate = searchRoutePoint.getVisitDate();
//            if (visitDate != null) searchSpecification = searchSpecification.and(visitDate(visitDate));
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
                    role(role).
                            and(country(searchRoutePoint.getCountry())).
                            and(cityEquals(searchRoutePoint.getCity()).or(cityIsNull())).
                            and(visitDate(searchRoutePoint.getVisitDate()).or(visitDateIsNull()));
            results.addAll(routePointRepository.findAll(searchSpecification));
        }
        return results;
    }
}
