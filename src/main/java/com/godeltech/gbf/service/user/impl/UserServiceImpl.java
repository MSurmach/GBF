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
import java.util.stream.Collectors;

import static com.godeltech.gbf.repository.specification.UserSpecs.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private TelegramUserRepository telegramUserRepository;
    private RoutePointService routePointService;

    @Override
    public Page<TelegramUser> findTelegramUsersBySearchDataAndRole(TelegramUser searchData, Role role, int pageNumber) {
        List<RoutePoint> searchRoutePoints = searchData.getRoutePoints();
        List<RoutePoint> availableRoutePoints =
                routePointService.findRoutePointsByNeededRoutePointsAndByRoleAndNotEqualToTelegramId(
                        searchRoutePoints,
                        role,
                        searchData.getTelegramId());
        if (availableRoutePoints.isEmpty()) return Page.empty();
        List<TelegramUser> foundTelegramUsers = availableRoutePoints.stream().
                map(RoutePoint::getTelegramUser).
                collect(Collectors.toSet()).
                stream().
                filter(telegramUser -> checkRoutePointsOrder(searchRoutePoints, telegramUser.getRoutePoints())).
                toList();
        if (foundTelegramUsers.isEmpty()) return Page.empty();
        List<Long> neededUserIds = foundTelegramUsers.stream().map(TelegramUser::getId).toList();
        Specification<TelegramUser> searchSpecification = idInRange(neededUserIds);
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
    public void removeByChangedAtBefore(LocalDate date) {
        telegramUserRepository.removeByChangedAtBefore(date);
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

    private boolean checkRoutePointsOrder(List<RoutePoint> givenRoute, List<RoutePoint> targetRoute) {

        var givenRouteSize = givenRoute.size();
        var targetPlace = 0;
        for (RoutePoint pointFromGivenRoute : givenRoute) {
            for (var tIndex = targetPlace; tIndex < targetRoute.size(); tIndex++) {
                var pointFromTargetRoute = targetRoute.get(tIndex);
                if (pointFromGivenRoute.isTheSameGeographical(pointFromTargetRoute)) {
                    targetPlace = tIndex;
                    givenRouteSize--;
                    break;
                }
            }
        }
        return givenRouteSize == 0;
    }


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
}
