package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.TelegramUser_;
import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<TelegramUser> byRoleEquals(Role role) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TelegramUser_.role), role);
    }

    public static Specification<TelegramUser> byPackageSizeEquals(String packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TelegramUser_.packageSize), packageSize);
    }

    public static Specification<TelegramUser> byPackageSizeIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(root.get(TelegramUser_.packageSize).isNull());
    }

    public static Specification<TelegramUser> byCompanionCountIsLessThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(TelegramUser_.companionCount), companionCount);
    }

    public static Specification<TelegramUser> byCompanionCountIsGreaterThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(TelegramUser_.companionCount), companionCount);
    }

    public static Specification<TelegramUser> byDocumentsIsGreaterThanOrEquals(boolean documents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(TelegramUser_.documents), documents);
    }

    public static Specification<TelegramUser> byDocumentsIsLessThanOrEquals(boolean documents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(TelegramUser_.documents), documents);
    }
}
