package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.model.UserRecord_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserRecordSpecs {

    public static Specification<UserRecord> byRole(Role role) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.role), role);
    }

    public static Specification<UserRecord> byCityFrom(String cityFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.cityFrom), cityFrom);
    }

    public static Specification<UserRecord> byCityTo(String cityTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.cityTo), cityTo);
    }

    public static Specification<UserRecord> byDateFrom(LocalDate dateFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.dateFrom), dateFrom);
    }

    public static Specification<UserRecord> byDateTo(LocalDate dateTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.dateTo), dateTo);
    }

    public static Specification<UserRecord> byPackageSize(String packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.packageSize), packageSize);
    }

    public static Specification<UserRecord> byCompanionCount(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.companionCount), companionCount);
    }

    public static Specification<UserRecord> byDocumentsExist(boolean documents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserRecord_.documents), documents);
    }
}
