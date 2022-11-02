package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

public class RoutePointSpecs {

    public static Specification<RoutePoint> roleAndNotEqualToTelegramId(Role role, Long telegramId) {
        return (root, query, criteriaBuilder) -> {
            Join<RoutePoint, TelegramUser> join = root.join("telegramUser");
            Predicate hasRole = criteriaBuilder.equal(join.get("role"), role);
            Predicate hasNotTelegramId = criteriaBuilder.notEqual(join.get("telegramUser"), telegramId);
            return criteriaBuilder.and(hasRole, hasNotTelegramId);
        };
    }

//    public static Specification<RoutePoint> byCitiesName(List<City> cities) {
//        return (root, query, criteriaBuilder) -> {
////            Predicate byCities = root.get("city").in(cities);
////            Expression<Long> offerIdCount = criteriaBuilder.count(root.get("offer"));
////            query.select(root.get("offer")).where(byCities).groupBy(root.get("offer")).having(criteriaBuilder.greaterThanOrEqualTo(offerIdCount, 2L));
////            return criteriaBuilder.and();
//            Predicate byCities = root.get("city").in(cities);
//            Expression<Long> offerIdCount = criteriaBuilder.count(root.get("offer"));
//            Root<RoutePoint> offer = query.subquery(Offer.class).from(RoutePoint.class).;
//            Subquery<Offer> offerds = offer.groupBy(root.get("offer"))
//                    .having(byCities, criteriaBuilder.greaterThanOrEqualTo(offerIdCount, 2L));
////            query.groupBy(root.get("offer"))
////                    .having(byCities,criteriaBuilder.greaterThanOrEqualTo(offerIdCount,2L));
//            return criteriaBuilder.equal(root.get("offer"),offer);
//
//        };
//    }
}
