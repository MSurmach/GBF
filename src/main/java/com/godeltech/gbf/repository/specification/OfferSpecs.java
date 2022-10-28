package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;

public class OfferSpecs {

    public static Specification<Offer> byTelegramUserId(Long telegramId) {
        return ((root, query, criteriaBuilder) -> {
            Join<Offer, TelegramUser> join = root.join("telegramUser");
            return criteriaBuilder.equal(join.get("telegramId"), telegramId);
        });
    }

    public static Specification<Offer> byDates(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate moreOrEqualStartDate = criteriaBuilder.greaterThanOrEqualTo(root.get("start_date"), startDate);
            Predicate lessOrEqualEndDate = criteriaBuilder.lessThanOrEqualTo(root.get("end_date"), endDate);
            return criteriaBuilder.and(moreOrEqualStartDate, lessOrEqualEndDate);
        };
    }


    public static Specification<Offer> byOfferId(List<Long> offers_id) {
        return (root, query, criteriaBuilder) -> root.in(offers_id);
    }

    public static Specification<Offer> byRole(Role role){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"),role);
    }

    public static Specification<Offer> byDeliveryLessOrEqual(Delivery delivery){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("delivery"),delivery);
    }
    public static Specification<Offer> byDeliveryMoreOrEqual(Delivery delivery){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("delivery"),delivery);
    }

}
