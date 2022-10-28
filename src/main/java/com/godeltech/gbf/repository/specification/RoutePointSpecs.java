package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class RoutePointSpecs {

    public static Specification<RoutePoint> roleAndNotEqualToTelegramId(Role role, Long telegramId) {
        return (root, query, criteriaBuilder) -> {
            Join<RoutePoint, TelegramUser> join = root.join("telegramUser");
            Predicate hasRole = criteriaBuilder.equal(join.get("role"), role);
            Predicate hasNotTelegramId = criteriaBuilder.notEqual(join.get("telegramId"), telegramId);
            return criteriaBuilder.and(hasRole, hasNotTelegramId);
        };
    }

    public static Specification<RoutePoint> byCitiesName(List<Integer> citiesId) {
        return (root, query, criteriaBuilder) -> {
            Predicate byCities = root.get("city_id").in(citiesId);
            query.groupBy(root.get("offer_id"))
                    .having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(byCities), 2L));
            return criteriaBuilder.and();

        };
    }
}
