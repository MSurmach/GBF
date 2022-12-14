package com.godeltech.gbf.service.route_point.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.repository.RoutePointRepository;
import com.godeltech.gbf.repository.specification.RoutePointSpecs;
import com.godeltech.gbf.service.route_point.RoutePointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.godeltech.gbf.model.Role.CLIENT;

@Service
@AllArgsConstructor
@Slf4j
public class RoutePointServiceImpl implements RoutePointService {

    private RoutePointRepository routePointRepository;
//
//    public List<RoutePoint> findRoutePointsByNeededRoutePointsAndByRoleAndNotEqualToTelegramId(List<RoutePoint> neededRoute,
//                                                                                               Role role,
//                                                                                               Long telegramId) {
//        log.info("Find route points by routes : {} , role : {}, user id : {}", neededRoute, role, telegramId);
//        List<RoutePoint> routePoints = new LinkedList<>();
//        for (RoutePoint neededRoutePoint : neededRoute) {
//            Specification<RoutePoint> searchSpecification =
//                    role == CLIENT ?
//                            buildClientSearchSpecification(neededRoutePoint, telegramId) :
//                            buildCourierSearchSpecification(neededRoutePoint, telegramId);
//            routePoints.addAll(routePointRepository.findAll(searchSpecification));
//        }
//        return routePoints;
//    }
//
//    @Override
//    public List<RoutePoint> findOffersIdByCities(List<RoutePoint> routePoints, Role role, Long offerId) {
//        log.info("Find offers id by routes :{} , role : {}, user id : {}", routePoints, role, offerId);
//        return routePointRepository.findAll(RoutePointSpecs.byCitiesName(routePoints.stream()
//                .map(routePoint -> routePoint.getCity()).collect(Collectors.toList())));
////                .and(RoutePointSpecs.roleAndNotEqualToTelegramId(role, offerId)));
//    }

    private Specification<RoutePoint> buildCourierSearchSpecification(RoutePoint routePoint, Long telegramId) {
//        Specification<RoutePoint> specification = roleAndNotEqualToTelegramId(COURIER, telegramId);
//        specification = specification.and(country(routePoint.getCountry()));
//        City city = routePoint.getCity();
//        if (city != null) specification = specification.and(city(city));
//        LocalDate startDate = routePoint.getStartDate();
//        LocalDate endDate = routePoint.getEndDate();
//        if (startDate != null)
//            specification = specification.
//                    and(startDateAfter(startDate).
//                            or(startDateBefore(startDate))).
//                    and(endDateBefore(endDate).
//                            or(endDateAfter(endDate)));
//        return specification;
        return null;
    }

    private Specification<RoutePoint> buildClientSearchSpecification(RoutePoint routePoint, Long telegramId) {
//        LocalDate startDate = routePoint.getStartDate();
//        LocalDate endDate = routePoint.getEndDate();
//        return roleAndNotEqualToTelegramId(CLIENT, telegramId).
//                and(country(routePoint.getCountry())).
//                and(city(routePoint.getCity()).
//                        or(cityIsNull()));
//                and(((startDateAfter(startDate).
//                        or(startDateBefore(startDate))).
//                        and(endDateBefore(endDate).
//                                or(endDateAfter(endDate)))).
//                        or(startDateIsNull()));
        return null;
    }

    @Override
    public List<Long> findOffersIdByRoutePoints(List<RoutePoint> routePoints) {
        log.info("Find id's of offers by route points : {}" ,routePoints);
        return routePointRepository.findOffersId(routePoints.stream().map(routePoint -> routePoint.getCity().getId())
                .collect(Collectors.toList()));

    }
}
