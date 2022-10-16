package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.TelegramUser_;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;

public class UserSpecs {

    public static Specification<TelegramUser> byIdIn(List<Long> userIds) {
        return (root, query, criteriaBuilder) -> {
            if (userIds != null && !userIds.isEmpty())
                return root.get("id").in(userIds);
            else return criteriaBuilder.and();
        };
    }

    public static Specification<TelegramUser> byPackageSizeEquals(String packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("packageSize"), packageSize);
    }

    public static Specification<TelegramUser> byPackageSizeIsNull() {
        return (root, query, criteriaBuilder) -> root.get("packageSize").isNull();
    }

    public static Specification<TelegramUser> byCompanionCountIsLessThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("companionCount"), companionCount);
    }

    public static Specification<TelegramUser> byCompanionCountIsGreaterThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("companionCount"), companionCount);
    }

    public static Specification<TelegramUser> byDocumentsIsGreaterThanOrEquals(boolean documentsExist) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("documentsExist"), documentsExist);
    }

    public static Specification<TelegramUser> byDocumentsIsLessThanOrEquals(boolean documentsExist) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("documentsExist"), documentsExist);
    }
}
