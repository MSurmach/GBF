package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.view.StateView;
import com.godeltech.gbf.view.impl.AlertStateView;
import com.godeltech.gbf.view.impl.DefaultStateView;
import com.godeltech.gbf.view.impl.RegistrationsMainStateView;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Service
@AllArgsConstructor
public class StateViewFactory implements Factory<StateView<? extends BotApiMethod<?>>> {
    private BeanFactory beanFactory;

    @Override
    public StateView<? extends BotApiMethod<?>> get(State state) {
        Class<? extends StateView<? extends BotApiMethod<?>>> stateView =
                switch (state) {
                    case REGISTRATIONS -> RegistrationsMainStateView.class;
                    case ALERT -> AlertStateView.class;
                    default -> DefaultStateView.class;
                };
        return beanFactory.getBean(stateView);
    }
}
