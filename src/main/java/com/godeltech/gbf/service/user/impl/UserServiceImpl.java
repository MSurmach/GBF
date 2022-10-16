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

    private Specification<TelegramUser> buildSpecificationForCouriersSearch(TelegramUser searchData) throws Exception {
        Role role = Role.COURIER;
        List<Long> availableTelegramUserIds = findTelegramUserIdsByRoutePointsAndRole(searchData.getRoutePoints(), role);
        Specification<TelegramUser> searchSpecification = byIdIn(availableTelegramUserIds);
        boolean documents = searchData.isDocumentsExist();
        if (documents)
            searchSpecification = searchSpecification.and(byDocumentsIsGreaterThanOrEquals(documents));
        String packageSize = searchData.getPackageSize();
        if (packageSize != null) searchSpecification = searchSpecification.and(byPackageSizeEquals(packageSize));
        searchSpecification = searchSpecification.and(byCompanionCountIsGreaterThanOrEqualTo(searchData.getCompanionCount()));
        return searchSpecification;
    }

    private Specification<TelegramUser> buildSpecificationForClientsSearch(TelegramUser searchData) throws Exception {
        Role role = Role.CLIENT;
        List<Long> availableTelegramUserIds = findTelegramUserIdsByRoutePointsAndRole(searchData.getRoutePoints(), role);
        Specification<TelegramUser> searchSpecification =
                byIdIn(availableTelegramUserIds).
                        and(byDocumentsIsLessThanOrEquals(searchData.isDocumentsExist()).
                                or(byPackageSizeEquals(searchData.getPackageSize()).or(byPackageSizeIsNull())).
                                or(byCompanionCountIsLessThanOrEqualTo(searchData.getCompanionCount())));
        return searchSpecification;
    }

    private List<Long> findTelegramUserIdsByRoutePointsAndRole(List<RoutePoint> searchRoutePoints, Role role) throws Exception {
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
        List<Long> availableTelegramUserIds = groupedByUserIdCountMap.entrySet().stream().
                filter(filterByRoutePointsSize).
                map(Map.Entry::getKey).
                toList();
        if (availableTelegramUserIds.isEmpty()) throw new Exception("Nothing was found");
        return availableTelegramUserIds;
    }

    @Override
    public Page<TelegramUser> findTelegramUsersBySearchDataAndRole(TelegramUser searchData, Role role, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Specification<TelegramUser> searchSpecification = null;
        try {
            if (role == Role.COURIER) {
                searchSpecification = buildSpecificationForCouriersSearch(searchData);
            }
            if (role == Role.CLIENT) {
                searchSpecification = buildSpecificationForClientsSearch(searchData);
            }
        } catch (Exception exception) {
            return null;
        }
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
        switch (userData.getRole()) {
            case REGISTRATIONS_VIEWER -> userData.setRole(Role.COURIER);
            case REQUESTS_VIEWER -> userData.setRole(Role.CLIENT);
        }
        TelegramUser forSave = ModelUtils.telegramUser(userData);
        telegramUserRepository.save(forSave);
    }
}
