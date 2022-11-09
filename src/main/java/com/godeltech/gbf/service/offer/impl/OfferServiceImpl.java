package com.godeltech.gbf.service.offer.impl;

import com.godeltech.gbf.event.NotificationEvent;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void save(SessionData sessionData) {
        log.info("Save offer with session date : {}",sessionData);
        Offer offer = ModelUtils.mapSessionDataToOffer(sessionData);
        TelegramUser telegramUser = telegramUserService.getOrCreateUser(sessionData.getTelegramUserId(), sessionData.getUsername());
        offer.setTelegramUser(telegramUser);
        offer=offerRepository.save(offer);
        applicationEventPublisher.publishEvent(new NotificationEvent(offer));
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
        return offerRepository.findOffersByTelegramUserIdAndRoleOrderByIdDesc(userId, role, pageable);
    }


    @Override
    @Transactional
    public void deleteOfferById(Long offerId) {
        log.info("Delete offer by id : {}", offerId);
        offerRepository.deleteById(offerId);
    }

    @Override
    public Page<Offer> findSuitableOffersByGivenOffer(Offer offer, int pageNumber) {
        log.info("Find offers by offer : {} and role : {}", offer, offer.getRole());
        Specification<Offer> specification = offer.getRole() == Role.CLIENT ?
                createSpecificationForSearchingCouriers(offer) :
                createSpecificationForSearchingClients(offer);
        Pageable pageable = PageRequest.of(pageNumber, 1);

        return specification == null ?
                Page.empty() :
                offerRepository.findAll(specification, pageable);
    }
    @Override
    public List<Offer> findSuitableOffersListByGivenOffer(Offer offer) {
        log.info("Find offers by offer : {} and role : {}", offer, offer.getRole());
        Specification<Offer> specification = offer.getRole() == Role.CLIENT ?
                createSpecificationForSearchingCouriers(offer) :
                createSpecificationForSearchingClients(offer);

        return offerRepository.findAll(specification);
    }

    @Override
    public Page<Offer> findAllByRole(Role role, int pageNumber) {
        log.info("Find all offers by role : {}",role);
        Pageable pageable = PageRequest.of(pageNumber,3);
        return offerRepository.findAllByRole(role,pageable);
    }


    private Specification<Offer> createSpecificationForSearchingClients(Offer givenOffer) {
        log.info("Create specification for courier");
        List<Long> offersId = routePointService.findOffersIdByRoutePoints(givenOffer.getRoutePoints());
        if (offersId.isEmpty())
            return null;
        List<Offer> offers = offerRepository.findAllById(offersId);
        offers = checkOfferOrder(offers, givenOffer.getRoutePoints());
        Specification<Offer> specification = getSpecificationForId(offers);
        specification = addSpecificationByRole(Role.CLIENT, specification);
//        specification = addSpecificationForExcludingUser(givenOffer.getTelegramUser().getId(), specification);
        specification = addSpecificationByDates(givenOffer.getStartDate(), givenOffer.getEndDate(), specification);
        specification = addSpecificationBySeatsForSearchingClients(givenOffer.getSeats(), specification);
        specification = addSpecificationByDeliveryForSearchingClients(givenOffer.getDelivery(), specification);
        return specification;
    }

    private Specification<Offer> createSpecificationForSearchingCouriers(Offer sessionData) {
        log.info("Create specification for Client");
        List<Long> offersId = routePointService.findOffersIdByRoutePoints(sessionData.getRoutePoints());
        if (offersId.isEmpty())
            return null;
        List<Offer> offers = offerRepository.findAllById(offersId);
        offers = checkOfferOrder(offers, sessionData.getRoutePoints());
        Specification<Offer> specification = getSpecificationForId(offers);
        specification = addSpecificationByRole(Role.COURIER, specification);
//        specification = addSpecificationForExcludingUser(sessionData.getTelegramUser().getId(), specification);
        specification = addSpecificationByDates(sessionData.getStartDate(), sessionData.getEndDate(), specification);
        specification = addSpecificationBySeatsForSearchingCouriers(sessionData.getSeats(), specification);
        specification = addSpecificationByDeliveryForSearchingCouriers(sessionData.getDelivery(), specification);
        return specification;
    }

    private List<Offer> checkOfferOrder(List<Offer> offers, List<RoutePoint> route) {
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

            maxRoute.lastIndexOf(pointFromMinRoute);
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

    private Specification<Offer> addSpecificationByRole(Role role, Specification<Offer> specification) {
        return specification.and(OfferSpecs.byRole(role));
    }

    private Specification<Offer> addSpecificationBySeatsForSearchingClients(int seats, Specification<Offer> specification) {
        if (seats == 0)
            return specification;
        return specification.and(OfferSpecs.bySeatsLess(seats).or(OfferSpecs.bySeatsIsNull()));
    }

    private Specification<Offer> addSpecificationByDeliveryForSearchingClients(Delivery delivery, Specification<Offer> specification) {
        if (delivery == null)
            return specification;
        return specification.and(OfferSpecs.byDeliveryLessOrEqualOrIsNull(delivery));
    }


    private Specification<Offer> getSpecificationForId(List<Offer> offerList) {
        List<Long> idList = offerList.stream().map(Offer::getId).collect(Collectors.toList());
        log.debug("Get specification with list of id : {}",idList);
        return OfferSpecs.byOfferId(idList);
    }

    private Specification<Offer> addSpecificationByDeliveryForSearchingCouriers(Delivery delivery, Specification<Offer> specification) {
        log.debug("Add client specification for delivery with income delivery :{}",delivery);
        if (delivery == null)
            return specification;
        return specification.and(OfferSpecs.byDeliveryGreaterOrEqual(delivery).or(OfferSpecs.byDeliveryIsNull()));
    }

    private Specification<Offer> addSpecificationBySeatsForSearchingCouriers(int seats, Specification<Offer> specification) {
        log.debug("Add client specification for seat with income value seats : {}",seats);
        if (seats == 0)
            return specification;
        return specification.and(OfferSpecs.bySeatsGreater(seats).or(OfferSpecs.bySeatsIsNull()));
    }

    private Specification<Offer> addSpecificationByDates(LocalDate startDate, LocalDate endDate, Specification<Offer> specification) {
        log.debug("Add date specification with income values : startDate : {} and endDate: {}",startDate,endDate);
        if (startDate == null) {
            return specification;
        }
        if (startDate.equals(endDate)) {
            return specification.and((OfferSpecs.startDateEqual(startDate)
                    .or(OfferSpecs.dateBetween(startDate)))
                    .or(OfferSpecs.startDateIsNull()));
        } else {
            return specification.and(OfferSpecs.datesBetween(startDate, endDate)
                    .or(OfferSpecs.startDateIsNull()));
        }
    }
}
