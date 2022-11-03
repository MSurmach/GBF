package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class OfferSpecs {

    public static Specification<Offer> byTelegramUserId(Long telegramId) {
        return ((root, query, criteriaBuilder) -> {
            Join<Offer, TelegramUser> join = root.join("telegramUser");
            return criteriaBuilder.equal(join.get("telegramId"), telegramId);
        });
    }

    public static Specification<Offer> startDateIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("startDate"));
    }
    public static Specification<Offer> startDateEqual(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startDate"), startDate);
    }

    public static Specification<Offer> dateBetween(LocalDate startDate){
        return (root, query, criteriaBuilder)  -> {
            Predicate dateGreater = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
            Predicate dateLess = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"),startDate);
            return criteriaBuilder.and(dateGreater,dateLess);
        };
    }

    public static Specification<Offer> datesBetween(LocalDate startDate,LocalDate endDate){
        return (root, query, criteriaBuilder) -> {
            Predicate betweenStartDate = criteriaBuilder.between(root.get("startDate"), startDate, endDate);
            Predicate betweenEndDate = criteriaBuilder.between(root.get("endDate"),startDate,endDate);
            return criteriaBuilder.or(betweenStartDate,betweenEndDate);
        };
    }
    public static Specification<Offer> startDateAfter(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Offer> startDateBeforeEndDate(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), startDate);
    }

    public static Specification<Offer> endDateAfter(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDate);
    }

    public static Specification<Offer> endDateAfterStartDate(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), endDate);
    }
    public static Specification<Offer> bySeatsLess(Integer seats){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("seats"),seats));
    }

    public static Specification<Offer> bySeatsGreater(Integer seats){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("seats"),seats));
    }
    public static Specification<Offer> bySeatsIsNull(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("seats")));
    }

    public static Specification<Offer> byOfferId(List<Long> offers_id) {
        return (root, query, criteriaBuilder) -> root.in(offers_id);
    }

    public static Specification<Offer> byRole(Role role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<Offer> byDeliveryGreaterOrEqual(Delivery delivery) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("delivery"), delivery);
    }
    public static Specification<Offer> byDeliveryLessOrEqualOrIsNull(Delivery delivery) {
        return (root, query, criteriaBuilder) ->{
            Predicate sizeLess = criteriaBuilder.lessThanOrEqualTo(root.get("delivery"), delivery);
            Predicate isNull = criteriaBuilder.isNull(root.get("delivery"));
            return criteriaBuilder.or(sizeLess,isNull);
        };
    }

    public static Specification<Offer> byDeliveryMoreOrEqual(Delivery delivery) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("delivery"), delivery);
    }
    public static Specification<Offer> byDateAfter(LocalDate date){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("endDate"),date);
    }

    public static Specification<Offer> byCitiesName(List<Integer> cities) {
        return (root, query, criteriaBuilder) -> {
            Join<Offer,RoutePoint> offerRoutePointJoin = root.join("routePoint");
            Predicate byCities = offerRoutePointJoin.get("city").in(cities);
            Expression<Long> offerIdCount = criteriaBuilder.count(offerRoutePointJoin.get("offer"));
            CriteriaQuery<?> offer = query.groupBy(offerRoutePointJoin.get("offer"))
                    .having(byCities, criteriaBuilder.greaterThanOrEqualTo(offerIdCount, 2L));
            return criteriaBuilder.and();
        };
    }

    public static Specification<Offer> byNotEqualUserId(Long telegramUserId) {
        return (root, query, criteriaBuilder) ->criteriaBuilder.notEqual(root.get("telegramUser"),telegramUserId);
    }
}
