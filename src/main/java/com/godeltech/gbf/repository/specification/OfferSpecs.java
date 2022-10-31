package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
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

    public static Specification<Offer> startDateAfter(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Offer> startDateBefore(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Offer> endDateAfter(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDate);
    }

    public static Specification<Offer> endDateBefore(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), endDate);
    }
    public static Specification<Offer> bySeatsLess(Integer seats){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("seats"),seats));
    }

    public static Specification<Offer> bySeatsGreater(Integer seats){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("seats"),seats));
    }

    public static Specification<Offer> byOfferId(List<Long> offers_id) {
        return (root, query, criteriaBuilder) -> root.in(offers_id);
    }

    public static Specification<Offer> byRole(Role role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<Offer> byDeliveryLessOrEqual(Delivery delivery) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("delivery"), delivery);
    }

    public static Specification<Offer> byDeliveryMoreOrEqual(Delivery delivery) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("delivery"), delivery);
    }

}
