package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecs {

    public static Specification<TelegramUser> idInRange(List<Long> userIds) {
        return (root, query, criteriaBuilder) -> {
            if (userIds != null && !userIds.isEmpty())
                return root.get("id").in(userIds);
            else return criteriaBuilder.and();
        };
    }

    public static Specification<TelegramUser> telegramIdNotEqualTo(Long telegramId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("telegramId"), telegramId);
    }

    public static Specification<TelegramUser> packageSizeIsLessThanOrEqualTo(int packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("packageSize"), packageSize);
    }

    public static Specification<TelegramUser> packageSizeIsGreaterThanOrEqualTo(int packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("packageSize"), packageSize);
    }

    public static Specification<TelegramUser> companionCountIsLessThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("companionCount"), companionCount);
    }

    public static Specification<TelegramUser> companionCountIsGreaterThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("companionCount"), companionCount);
    }

    public static Specification<TelegramUser> documentsExist() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("documentsExist"), true);
    }

    public static Specification<TelegramUser> documentsIsLessThanOrEquals(boolean documentsExist) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("documentsExist"), documentsExist);
    }
}
