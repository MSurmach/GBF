package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.view.ViewType;
import com.godeltech.gbf.service.view.impl.DefaultViewType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ViewFactory implements Factory<ViewType<? extends BotApiMethod<?>>> {
    private final Map<State, ViewType<? extends BotApiMethod<?>>> viewContext;

    @Autowired
    public ViewFactory(List<ViewType<? extends BotApiMethod<?>>> viewTypeContext) {
        this.viewContext = viewTypeContext.stream()
                .collect(Collectors.toMap(ViewType::getState, Function.identity()));
    }

    @Override
    public ViewType<? extends BotApiMethod<?>> get(State state) {
        return viewContext.getOrDefault(state,
                (ViewType<? extends BotApiMethod<?>>) new DefaultViewType());

    }
}
