package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.view.StateView;
import com.godeltech.gbf.view.impl.DefaultStateView;
import com.godeltech.gbf.view.impl.RegistrationsMainStateView;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@AllArgsConstructor
public class StateViewFactory implements Factory<StateView<? extends SendMessage>> {
    private BeanFactory beanFactory;

    @Override
    public StateView<? extends SendMessage> get(State state) {
        Class<? extends StateView<SendMessage>> stateView =
                switch (state) {
                    case REGISTRATIONS_MAIN -> RegistrationsMainStateView.class;
                    default -> DefaultStateView.class;
                };
        return beanFactory.getBean(stateView);
    }
}
