package com.godeltech.gbf.repository.specification;

import com.godeltech.gbf.model.UserData;
import org.springframework.data.jpa.domain.Specification;

public class UserDataSpecs {
    public static Specification<UserData> byCityFrom(String cityFrom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(), cityFrom);
    }
}
