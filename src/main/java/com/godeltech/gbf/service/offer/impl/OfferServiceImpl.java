package com.godeltech.gbf.service.offer.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.OfferRepository;
import com.godeltech.gbf.repository.specification.OfferSpecs;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.route_point.RoutePointService;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final TelegramUserService telegramUserService;
    private final RoutePointService routePointService;

    @Override
    @Transactional
    public void save(SessionData sessionData) {
        switch (sessionData.getRole()) {
            case REGISTRATIONS_VIEWER -> sessionData.setRole(Role.COURIER);
            case REQUESTS_VIEWER -> sessionData.setRole(Role.CLIENT);
        }
        Offer newOffer = ModelUtils.mapSessionDataToOffer(sessionData);
        TelegramUser telegramUser = telegramUserService.getOrCreateUser(sessionData.getTelegramUserId(), sessionData.getUsername());
        newOffer.setTelegramUser(telegramUser);
        offerRepository.save(newOffer);
    }

    @Override
    @Transactional
    public void deleteExpiredOffersByDate(LocalDate expiredDate) {
        log.info("delete expired offers by date : {}", expiredDate);
        List<Offer> expiredOffers = offerRepository.findAll(OfferSpecs.byDateAfter(expiredDate));
        offerRepository.deleteAll(expiredOffers);
    }

    @Override
    public Page<Offer> findAllOffersByUserIdAndRole(Long userId, Role role, int pageNumber) {
        log.info("Find offers by user id : {} and role : {} ", userId, role);
        Pageable pageable = PageRequest.of(pageNumber, 1);
        return offerRepository.findOffersByTelegramUserIdAndRole(userId, role, pageable);

    }

    @Override
    @Transactional
    public void deleteOfferById(Long offerId) {
        log.info("Delete offer by id : {}", offerId);
        offerRepository.deleteById(offerId);
    }

    @Override
    public Page<Offer> findAllOffersBySessionDataAndRole(SessionData sessionData, Role client, int pageNumber) {
        log.info("Find offers by session date : {} and role : {}", sessionData, client);
        Specification<Offer> specification = client == Role.CLIENT ?
                createSpecificationForClient(sessionData) :
                createSpecificationForCourier(sessionData);
        Pageable pageable = PageRequest.of(pageNumber, 1);

        return specification == null ?
                Page.empty() :
                offerRepository.findAll(specification, pageable);
    }


    private Specification<Offer> createSpecificationForCourier(SessionData sessionData) {
        log.info("Create specification for courier");
        List<Long> offersId = routePointService.findOffersIdByRoutePoints(sessionData.getRoute());
        if (offersId.isEmpty())
            return null;
        List<Offer> offers = offerRepository.findAllById(offersId);
        offers = checkOfferOrder(offers, sessionData.getRoute());

        Specification<Offer> specification = getSpecificationForId(offers);
        specification = addSpecificationForRole(Role.COURIER, specification);
        specification = addSpecificationForExcludingUser(sessionData.getTelegramUserId(), specification);
        specification = addSpecificationForDates(sessionData.getStartDate(), sessionData.getEndDate(), specification);
        specification = addCourierSpecificationForSeats(sessionData.getSeats(), specification);
        specification = addSpecificationForCourierDelivery(sessionData.getDelivery(), specification);
        return specification;
    }

    private Specification<Offer> createSpecificationForClient(SessionData sessionData) {
        log.info("Create specification for Client");
        List<Long> offersId = routePointService.findOffersIdByRoutePoints(sessionData.getRoute());
        if (offersId.isEmpty())
            return null;
        List<Offer> offers = offerRepository.findAllById(offersId);
        checkOfferOrder(offers, sessionData.getRoute());
        Specification<Offer> specification = getSpecificationForId(offers);
        specification = addSpecificationForRole(Role.CLIENT, specification);
        specification = addSpecificationForExcludingUser(sessionData.getTelegramUserId(), specification);
        specification = addSpecificationForDates(sessionData.getStartDate(), sessionData.getEndDate(), specification);
        specification = addClientSpecificationForSeats(sessionData.getSeats(), specification);
        specification = addClientSpecificationForDelivery(sessionData.getDelivery(), specification);
        return specification;
    }

    private List<Offer> checkOfferOrder(List<Offer> offers, LinkedList<RoutePoint> route) {
        return offers.stream()
                .filter(offer -> checkRoutePointsOrder(route, offer.getRoutePoints()))
                .collect(Collectors.toList());
    }

    private boolean checkRoutePointsOrder(List<RoutePoint> givenRoute, List<RoutePoint> targetRoute) {
        List<RoutePoint> minRoute, maxRoute;
        if (givenRoute.size() < targetRoute.size()) {
            minRoute = givenRoute;
            maxRoute = targetRoute;
        } else {
            minRoute = targetRoute;
            maxRoute = givenRoute;
        }
        var minRouteSize = minRoute.size();
        var place = 0;
        for (RoutePoint pointFromMinRoute : minRoute) {
            for (var index = place; index < maxRoute.size(); index++) {
                var pointFromMaxRoute = maxRoute.get(index);
                if (pointFromMinRoute.getCity().equals(pointFromMaxRoute.getCity())) {
                    place = index;
                    minRouteSize--;
                    break;
                }
            }
        }
        return minRouteSize == 0;
    }

    private Specification<Offer> addSpecificationForExcludingUser(Long telegramUserId, Specification<Offer> specification) {
        return specification.and(OfferSpecs.byNotEqualUserId(telegramUserId));
    }

    private Specification<Offer> addSpecificationForRole(Role role, Specification<Offer> specification) {
        return specification.and(OfferSpecs.byRole(role));
    }

    private Specification<Offer> addCourierSpecificationForSeats(int seats, Specification<Offer> specification) {
        if (seats == 0)
            return specification;
        return specification.and(OfferSpecs.bySeatsGreater(seats));
    }

    private Specification<Offer> addSpecificationForCourierDelivery(Delivery delivery, Specification<Offer> specification) {
        if (delivery == null)
            return specification;
        return specification.and(OfferSpecs.byDeliveryLessOrEqualOrIsNull(delivery));
    }


    private Specification<Offer> getSpecificationForId(List<Offer> offerList) {
        return OfferSpecs.byOfferId(offerList.stream().map(Offer::getId).collect(Collectors.toList()));
    }

    private Specification<Offer> addClientSpecificationForDelivery(Delivery delivery, Specification<Offer> specification) {
        if (delivery == null)
            return specification;
        return specification.and(OfferSpecs.byDeliveryLessOrEqual(delivery));
    }

    private Specification<Offer> addClientSpecificationForSeats(int seats, Specification<Offer> specification) {
        if (seats == 0)
            return specification;
        return specification.and(OfferSpecs.bySeatsLess(seats));
    }

    private Specification<Offer> addSpecificationForDates(LocalDate startDate, LocalDate endDate, Specification<Offer> specification) {
        if (startDate == null) {
            return specification;
        }
        if (startDate.equals(endDate)) {
            return specification.and((OfferSpecs.startDateEqual(startDate).or(OfferSpecs.dateBetween(startDate))));
        } else {
            return specification.and(OfferSpecs.datesBetween(startDate, endDate));
        }
    }
}
