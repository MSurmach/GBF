package com.godeltech.gbf.gui.message;

import com.godeltech.gbf.LocalMessageSource;
import org.springframework.data.domain.Page;

public interface PaginationInfo<T> {
    String PAGINATION_COUNT_INFO_CODE = "pagination.count.info";

    default String paginationInfoLocalMessage(Page<T> page, LocalMessageSource lms) {
        return lms.getLocaleMessage(
                PAGINATION_COUNT_INFO_CODE,
                String.valueOf(page.getTotalElements())
        );
    }
}
