package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.model.State;

public interface Factory<T> {
    T get(State state);
}
