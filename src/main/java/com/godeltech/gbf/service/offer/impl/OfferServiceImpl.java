package com.godeltech.gbf.service.offer.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.Offer;
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
import java.util.ArrayList;
import java.util.List;

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
        Specification<Offer> specification = getSpecificationForId(offersId);
        List<Specification<Offer>> specifications = new ArrayList<>();
        addSpecificationForRole(Role.COURIER,specifications);
//        addSpecificationForExcludingUser(sessionData.getTelegramUserId(),specifications);
        addSpecificationForDates(sessionData.getStartDate(), sessionData.getEndDate(), specifications);
        addCourierSpecificationForSeats(sessionData.getSeats(), specifications);
        addSpecificationForCourierDelivery(sessionData.getDelivery(), specifications);
        specifications.forEach(specification::and);
        return specification;
    }
    private Specification<Offer> createSpecificationForClient(SessionData sessionData) {
        log.info("Create specification for Client");
        List<Long> routePoints =
                routePointService.findOffersIdByRoutePoints(sessionData.getRoute());
//                Role.COURIER,
//                sessionData.getTelegramUserId());
        if (routePoints.isEmpty())
            return null;
        Specification<Offer> specification = getSpecificationForId(routePoints);
        List<Specification<Offer>> specifications = new ArrayList<>();
        addSpecificationForRole(Role.CLIENT,specifications);
//        addSpecificationForExcludingUser(sessionData.getTelegramUserId(),specifications);
        addSpecificationForDates(sessionData.getStartDate(), sessionData.getEndDate(), specifications);
        addClientSpecificationForSeats(sessionData.getSeats(), specifications);
        addSpecificationForDelivery(sessionData.getDelivery(), specifications);
        specifications.forEach(specification::and);
        return specification;
    }
    private void addSpecificationForExcludingUser(Long telegramUserId, List<Specification<Offer>> specifications) {
        specifications.add(OfferSpecs.byNotEqualUserId(telegramUserId));
    }

    private void addSpecificationForRole(Role role, List<Specification<Offer>> specifications) {
        specifications.add(OfferSpecs.byRole(role));
    }

    private void addCourierSpecificationForSeats(int seats, List<Specification<Offer>> specifications) {
        if (seats == 0)
            return;
        specifications.add(OfferSpecs.bySeatsGreater(seats));
    }

    private void addSpecificationForCourierDelivery(Delivery delivery, List<Specification<Offer>> specifications) {
        if (delivery == null)
            return;
        specifications.add(OfferSpecs.byDeliveryLessOrEqualOrIsNull(delivery));
    }



    private Specification<Offer> getSpecificationForId(List<Long> idList) {
        return OfferSpecs.byOfferId(idList);
    }

    private void addSpecificationForDelivery(Delivery delivery, List<Specification<Offer>> specifications) {
        if (delivery == null)
            return;
        specifications.add(OfferSpecs.byDeliveryLessOrEqual(delivery));
    }

    private void addClientSpecificationForSeats(int seats, List<Specification<Offer>> specifications) {
        if (seats == 0)
            return;
        specifications.add(OfferSpecs.bySeatsLess(seats));
    }

    private void addSpecificationForDates(LocalDate startDate, LocalDate endDate, List<Specification<Offer>> specifications) {
        if (startDate == null) {
            return;
        }
        if (startDate.equals(endDate)) {
            specifications.add(OfferSpecs.startDateEqual(startDate).or(OfferSpecs.dateBetween(startDate)));
        } else {
            specifications.add(OfferSpecs.datesBetween(startDate, endDate));
        }
    }
}
