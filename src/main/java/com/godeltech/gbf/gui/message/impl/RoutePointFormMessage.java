package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoutePointFormMessage implements Message {
    private final LocalMessageSource lms;
    private final DetailsCreator detailsCreator;
    public final static String COURIER_HEADER_CODE = "routePointForm.courier.header";
    public final static String CLIENT_HEADER_CODE = "routePointForm.client.header";

    @Override
    public String getMessage(UserData userData) {
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        String header = switch (userData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(COURIER_HEADER_CODE, userData.getUsername());
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(CLIENT_HEADER_CODE, userData.getUsername());
        };
        return header + detailsCreator.createRoutePointDetails(tempRoutePoint);
    }
}
