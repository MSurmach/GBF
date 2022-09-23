package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.view.View;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StateViewFactory {
    private BeanFactory beanFactory;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public View getView(State state) {

        switch (state) {


            case MENU -> ;
            default -> null;
        }
        return applicationContext.getBean(className);
    }
}
