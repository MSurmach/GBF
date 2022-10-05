package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserData_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserDataSpecs {
    public static Specification<UserData> byCityFrom(String cityFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.cityFrom), cityFrom);
    }

    public static Specification<UserData> byCityTo(String cityTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.cityTo), cityTo);
    }

    public static Specification<UserData> byDateFrom(LocalDate dateFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.dateFrom), dateFrom);
    }

    public static Specification<UserData> byDateTo(LocalDate dateTo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.dateTo), dateTo);
    }

    public static Specification<UserData> byPackageSize(String packageSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.packageSize), packageSize);
    }

    public static Specification<UserData> byCompanionCount(int companionCount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.companionCount), companionCount);
    }

    public static Specification<UserData> byDocumentsExist(boolean documents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserData_.documents), documents);
    }
}
