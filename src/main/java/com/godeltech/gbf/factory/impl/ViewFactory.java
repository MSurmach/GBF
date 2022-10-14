package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.view.View;
import com.godeltech.gbf.service.view.impl.DefaultView;
import com.godeltech.gbf.service.view.impl.FormView;
import com.godeltech.gbf.service.view.impl.PaginatedView;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Service
@AllArgsConstructor
public class ViewFactory implements Factory<View<? extends BotApiMethod<?>>> {
    private BeanFactory beanFactory;

    @Override
    public View<? extends BotApiMethod<?>> get(State state) {
        Class<? extends View<? extends BotApiMethod<?>>> stateView =
                switch (state) {
                    case REGISTRATIONS, COURIERS_LIST_RESULT, CLIENTS_LIST_RESULT, REQUESTS -> PaginatedView.class;
                    case FORM -> FormView.class;
                    default -> DefaultView.class;
                };
        return beanFactory.getBean(stateView);
    }
}
