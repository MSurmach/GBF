package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.service.route_point.RoutePointService;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.godeltech.gbf.repository.specification.UserSpecs.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private TelegramUserRepository telegramUserRepository;
    private RoutePointService routePointService;

    private <T> Specification<T> attach(Specification<T> to, Specification<T> specification) {
        if (to == null) to = specification;
        return to.and(specification);
    }

    private Specification<TelegramUser> cargoSpecificationForCouriersSearch(TelegramUser searchData) {
        Specification<TelegramUser> cargoSpecification = null;
        boolean documentsExist = searchData.isDocumentsExist();
        if (documentsExist) cargoSpecification = attach(cargoSpecification, documentsExist());
        int packageSize = searchData.getPackageSize();
        if (packageSize != 0)
            cargoSpecification = attach(cargoSpecification, packageSizeIsGreaterThanOrEqualTo(packageSize));
        int companionCount = searchData.getCompanionCount();
        if (companionCount != 0)
            cargoSpecification = attach(cargoSpecification, companionCountIsGreaterThanOrEqualTo(companionCount));
        return cargoSpecification;
    }

    private Specification<TelegramUser> cargoSpecificationForClientsSearch(TelegramUser searchData) {
        return documentsIsLessThanOrEquals(searchData.isDocumentsExist()).
                or(packageSizeIsLessThanOrEqualTo(searchData.getPackageSize())).
                or(companionCountIsLessThanOrEqualTo(searchData.getCompanionCount()));
    }

    private List<Long> findTelegramUserIdsByRoutePointsAndRole(List<RoutePoint> searchRoutePoints, Role role) {
        Predicate<Map.Entry<Long, Long>> filterByRoutePointsSize = null;
        List<RoutePoint> roleRoutePoints = null;
        if (role == Role.COURIER) {
            filterByRoutePointsSize = entry -> entry.getValue() >= searchRoutePoints.size();
            roleRoutePoints = routePointService.findCourierRoutePointsByRoutePoints(searchRoutePoints);
        }
        if (role == Role.CLIENT) {
            filterByRoutePointsSize = entry -> entry.getValue() <= searchRoutePoints.size();
            roleRoutePoints = routePointService.findClientRoutePointsByRoutePoints(searchRoutePoints);
        }
        Function<RoutePoint, Long> routePointToUserIdFunction = routePoint -> routePoint.getTelegramUser().getId();
        Map<Long, Long> groupedByUserIdCountMap = roleRoutePoints.stream().
                collect(Collectors.groupingBy(routePointToUserIdFunction, Collectors.counting()));
        return groupedByUserIdCountMap.entrySet().stream().
                filter(filterByRoutePointsSize).
                map(Map.Entry::getKey).
                toList();
    }

    @Override
    public Page<TelegramUser> findTelegramUsersBySearchDataAndRole(TelegramUser searchData, Role role, int pageNumber) {
        List<Long> possibleUsersIds = findTelegramUserIdsByRoutePointsAndRole(searchData.getRoutePoints(), role);
        if (possibleUsersIds.isEmpty()) return null;
        Specification<TelegramUser> searchSpecification = idInRange(possibleUsersIds);
        if (role == Role.COURIER)
            searchSpecification = searchSpecification.and(cargoSpecificationForCouriersSearch(searchData));
        if (role == Role.CLIENT)
            searchSpecification = searchSpecification.and(cargoSpecificationForClientsSearch(searchData));
        Pageable pageable = PageRequest.of(pageNumber, 1);
        return telegramUserRepository.findAll(searchSpecification, pageable);
    }

    @Override
    public Page<TelegramUser> findUsersByTelegramIdAndRole(Long telegramId, Role role, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        return telegramUserRepository.findUsersByTelegramIdAndRole(telegramId, role, pageable);
    }

    @Override
    public void removeByExpiredAtBefore(LocalDate date) {
        telegramUserRepository.removeByExpiredAtBefore(date);
    }

    @Override
    public void removeByChangedAtAfter(LocalDate date) {
        telegramUserRepository.removeByChangedAtAfter(date);
    }

    @Override
    public void deleteById(Long userId) {
        telegramUserRepository.deleteById(userId);
    }

    @Override
    public void save(UserData userData) {
        TelegramUser forSave = ModelUtils.telegramUser(userData);
        switch (userData.getRole()) {
            case REGISTRATIONS_VIEWER -> forSave.setRole(Role.COURIER);
            case REQUESTS_VIEWER -> forSave.setRole(Role.CLIENT);
        }
        telegramUserRepository.save(forSave);
    }
}
