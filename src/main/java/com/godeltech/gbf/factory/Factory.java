package com.godeltech.gbf.factory;

import com.godeltech.gbf.model.State;

public interface Factory<T> {
    T get(State state);
}
