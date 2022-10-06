package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CouriersListText implements Text {
    public final static String COURIERS_LIST_INITIAL_MESSAGE = "couriers.list.initialMessage";
    public final static String COURIERS_LIST_HEADER = "couriers.list.header";
    public final static String COURIERS_LIST_PAGINATION_INFO = "couriers.list.pagination.info";
    private LocalMessageSource localMessageSource;
    private SummaryDataText summaryDataText;

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(COURIERS_LIST_HEADER, userData.getUsername()) +
                summaryDataText.getText(userData);
    }

    public String initialMessage() {
        return localMessageSource.getLocaleMessage(COURIERS_LIST_INITIAL_MESSAGE);
    }

    public String paginationInfoMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(COURIERS_LIST_PAGINATION_INFO,
                String.valueOf(userData.getRecords().size()), String.valueOf(1), String.valueOf(1));
    }
}
