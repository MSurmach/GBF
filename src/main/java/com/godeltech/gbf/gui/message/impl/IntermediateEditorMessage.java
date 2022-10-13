package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.godeltech.gbf.model.db.Status.INTERMEDIATE;

@Component
@AllArgsConstructor
public class IntermediateEditorMessage implements Message {
    public final static String INTERMEDIATE_EDITOR_DETAILS_HEADER_CODE = "intermediateEditor.details.header";
    private LocalMessageSource lms;
    private MessageUtil messageUtil;

    @Override
    public String getMessage(UserData userData) {
        String header = lms.getLocaleMessage(INTERMEDIATE_EDITOR_DETAILS_HEADER_CODE);
        List<RoutePoint> intermediateRoutePoints = userData.getRoutePoints().stream().
                filter(routePoint -> routePoint.getStatus() == INTERMEDIATE).
                toList();
        String intermediatePointsRoute = messageUtil.buildRoute(intermediateRoutePoints);
        return header + intermediatePointsRoute;
    }
}
