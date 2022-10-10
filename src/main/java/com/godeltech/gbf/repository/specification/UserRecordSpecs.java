package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.model.UserRecord_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserRecordSpecs {

    public static Specification<UserRecord> byRoleEquals(Role role) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.role), role);
    }

    public static Specification<UserRecord> byCityFromEquals(String cityFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.cityFrom), cityFrom);
    }

    public static Specification<UserRecord> byCityToEquals(String cityTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.cityTo), cityTo);
    }

    public static Specification<UserRecord> byDateFromEquals(LocalDate dateFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.dateFrom), dateFrom);
    }

    public static Specification<UserRecord> byDateFromIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(root.get(UserRecord_.dateFrom).isNull());
    }

    public static Specification<UserRecord> byDateToEquals(LocalDate dateTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.dateTo), dateTo);
    }

    public static Specification<UserRecord> byDateToIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(root.get(UserRecord_.dateTo).isNull());
    }


    public static Specification<UserRecord> byPackageSizeEquals(String packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.packageSize), packageSize);
    }

    public static Specification<UserRecord> byPackageSizeIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(root.get(UserRecord_.packageSize).isNull());
    }

    public static Specification<UserRecord> byCompanionCountIsLessThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(UserRecord_.companionCount), companionCount);
    }

    public static Specification<UserRecord> byCompanionCountIsGreaterThanOrEqualTo(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(UserRecord_.companionCount), companionCount);
    }

    public static Specification<UserRecord> byDocumentsIsGreaterThanOrEquals(boolean documents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(UserRecord_.documents), documents);
    }

    public static Specification<UserRecord> byDocumentsIsLessThanOrEquals(boolean documents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(UserRecord_.documents), documents);
    }
}
