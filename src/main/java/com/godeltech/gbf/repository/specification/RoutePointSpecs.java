package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;

public class RoutePointSpecs {

    public static Specification<RoutePoint> role(Role role) {
        return (root, query, criteriaBuilder) -> {
            Join<RoutePoint, TelegramUser> join = root.join("telegramUser");
            return criteriaBuilder.equal(join.get("role"), role);
        };
    }

    public static Specification<RoutePoint> country(Country country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country"), country);
    }

    public static Specification<RoutePoint> city(City city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<RoutePoint> cityIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("city"));
    }

    public static Specification<RoutePoint> visitDate(LocalDate visitDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visitDate"), visitDate);
    }

    public static Specification<RoutePoint> startDateIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("startDate"));
    }

    public static Specification<RoutePoint> startDateAfter(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<RoutePoint> startDateBefore(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<RoutePoint> endDateAfter(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDate);
    }

    public static Specification<RoutePoint> endDateBefore(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), endDate);
    }
}
