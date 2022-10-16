package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class RoutePointSpecs {

    public static Specification<RoutePoint> byRole(Role role){
        return (root, query, criteriaBuilder) -> {
            Join<RoutePoint, TelegramUser> join = root.join("telegramUser");
            return criteriaBuilder.equal(join.get("role"), role);
        };
    }

    public static Specification<RoutePoint> byCountry(Country country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country"), country);
    }

    public static Specification<RoutePoint> byCityEquals(City city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<RoutePoint> byCityIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("city"));
    }

    public static Specification<RoutePoint> byVisitDate(LocalDate visitDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visitDate"), visitDate);
    }

    public static Specification<RoutePoint> byVisitDateIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("visitDate"));
    }
}
